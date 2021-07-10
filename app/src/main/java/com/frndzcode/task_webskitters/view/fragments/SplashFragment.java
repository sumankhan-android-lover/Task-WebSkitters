package com.frndzcode.task_webskitters.view.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frndzcode.task_webskitters.MyApplication;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {
    private FragmentSplashBinding binding;
    private AppCompatActivity activity;
    private NavController navController;
    private SharedPreferences appPrefs;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bindView(view);
    }

    private void bindView(View view) {
        activity = (AppCompatActivity) getActivity();
        appPrefs = ((MyApplication) activity.getApplicationContext()).getAppPrefs();

        navController = Navigation.findNavController(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (appPrefs.getBoolean("intro_status",false)){
                    //navController.navigate(R.id.action_splashFragment_to_homeFragment);
                }else {
                    //navController.navigate(R.id.action_splashFragment_to_introFragment);
                }
            }
        },1500);

    }
}