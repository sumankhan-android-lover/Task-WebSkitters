package com.frndzcode.task_webskitters.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.frndzcode.task_webskitters.MyApplication;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ActivityMainBinding;
import com.frndzcode.task_webskitters.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private SharedPreferences appPrefs;
    private SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        bindActivity();
    }

    private void bindActivity() {
        appPrefs = ((MyApplication) getApplicationContext()).getAppPrefs();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (appPrefs.getBoolean("intro_status",false)){
                    intent = new Intent(activity,MainActivity.class);
                }else {
                    intent = new Intent(activity,IntroActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1500);
    }
}