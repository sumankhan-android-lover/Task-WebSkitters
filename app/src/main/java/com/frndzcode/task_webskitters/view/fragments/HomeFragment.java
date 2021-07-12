package com.frndzcode.task_webskitters.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.frndzcode.task_webskitters.databinding.FragmentHomeBinding;
import com.frndzcode.task_webskitters.model.HomeModel;
import com.frndzcode.task_webskitters.view.adapters.HomeAdapter;
import com.frndzcode.task_webskitters.viewModel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment /*implements InterfaceItemSelection */ {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private AppCompatActivity activity;
    private HomeViewModel viewModel;
    private HomeAdapter entriesAdapter;
    private ArrayList<HomeModel> itemList = new ArrayList<>();
    private ArrayList<HomeModel> filterList = new ArrayList<>();
    private ArrayList<HomeModel> selectionList = new ArrayList<>();
    private int selectionSize = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initListener();
    }

    private void bindView(View view) {
        activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);
        Log.e(TAG, "bindView: size 1" + selectionSize);

        if (activity != null) {
            viewModel = new ViewModelProvider(activity).get(HomeViewModel.class);
            viewModel.getEntriesMutableLiveData().observe(activity, this::setDataOnRecyclerView);
        }
        selectionList = new ArrayList<>();

    }

    private void setDataOnRecyclerView(ArrayList<HomeModel> homeModels) {
        itemList.addAll(homeModels);
        filterList.addAll(itemList);
        binding.recycler.setLayoutManager(new LinearLayoutManager(activity));
        binding.recycler.setHasFixedSize(true);
        entriesAdapter = new HomeAdapter(filterList, activity, HomeFragment.this);
        binding.recycler.setAdapter(entriesAdapter);
    }

    private void initListener() {
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProduct(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterProduct(String data) {
        if (data.length() > 1 && !data.trim().equals("")) {
            filterList.clear();
            for (int i = 0; i < itemList.size(); i++) {
                HomeModel model = itemList.get(i);
                if (model.getTitle().toLowerCase().contains(data.toLowerCase())) {
                    filterList.add(model);
                }
            }

            if (filterList.size() == 0)
                showNoData();

            entriesAdapter.notifyDataSetChanged();
        } else {
            filterList.clear();
            filterList.addAll(itemList);
            entriesAdapter.notifyDataSetChanged();
        }
    }

    private void showNoData() {

    }

    public void clickItem(int size) {
        Log.e(TAG, "bindView: selection count 1" + size);
        if (size > 0) {
            activity.getSupportActionBar().setTitle(String.valueOf(size));
        } else {
            activity.getSupportActionBar().setTitle(String.valueOf(0));
        }

    }

}