package com.frndzcode.task_webskitters.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndJSONArrayRequestListener;
import com.frndzcode.task_webskitters.model.HomeModel;
import com.frndzcode.task_webskitters.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.Response;

public class HomeViewModel extends ViewModel {
    private static final String TAG = HomeViewModel.class.getSimpleName();
    private ArrayList<HomeModel> entriesList;
    MutableLiveData<ArrayList<HomeModel>> entriesLiveData;


    public HomeViewModel() {
        entriesList = new ArrayList<>();
        entriesLiveData = new MutableLiveData<>();

        //this method call for api
        init();
    }

    public MutableLiveData<ArrayList<HomeModel>> getEntriesMutableLiveData() {
        return entriesLiveData;
    }

    private void init() {
        String url = Constants.BASE_URL + Constants.HOME_IMAGE_URL;

        AndroidNetworking.get(url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponseAndJSONArray(new OkHttpResponseAndJSONArrayRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, JSONArray response) {
                        Log.e(TAG, "onResponse: " + response);
                        parseEntriesResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onErrorResponse: " + anError.getErrorDetail());
                    }
                });
    }

    private void parseEntriesResponse(JSONArray response) {
        entriesList = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<HomeModel>>() {
        }.getType());
        entriesLiveData.setValue(entriesList);
    }
}
