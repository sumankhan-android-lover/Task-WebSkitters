package com.frndzcode.task_webskitters.view.fragments;

import android.os.Bundle;
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

public class HomeFragment extends Fragment /*implements InterfaceItemSelection */{

    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;
    private AppCompatActivity activity;
    private HomeViewModel viewModel;
    private HomeAdapter entriesAdapter;
    private ArrayList<HomeModel> selectionList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initListener();
        countSelectionData();
    }

    private void bindView(View view) {
        activity = (AppCompatActivity) getActivity();
        if (activity!=null) {
            viewModel =new ViewModelProvider(activity).get(HomeViewModel.class);
            viewModel.getEntriesMutableLiveData().observe(activity,this::setDataOnRecyclerView);
        }
        selectionList = new ArrayList<>();

    }

    private void setDataOnRecyclerView(ArrayList<HomeModel> homeModels) {
        binding.recycler.setLayoutManager(new LinearLayoutManager(activity));
        binding.recycler.hasFixedSize();
        entriesAdapter = new HomeAdapter(homeModels, activity, HomeFragment.this);
        binding.recycler.setAdapter(entriesAdapter);
    }

    private void initListener() {

    }

    private void countSelectionData() {
//        selectionList.clear();
//        if (selectionList.size()>0)
//            selectionList = entriesAdapter.getSelectedItem();
//        Log.e(TAG, "bindView: selection count 1"+ selectionList.size());
    }

    public void clickItem(int size) {
        Log.e(TAG, "bindView: selection count 1"+ size);
    }
//
//    @Override
//    public void onItemSelected(HomeModel item) {
//        selectionList = entriesAdapter.getSelectedItem();
//    }
}