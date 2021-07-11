package com.frndzcode.task_webskitters.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.frndzcode.task_webskitters.MyApplication;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ActivityMainBinding;
import com.frndzcode.task_webskitters.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int CHECK_PERMISSIONS = 0x10;
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
        checkLocationPermission();
    }

    private void bindActivity() {
        appPrefs = ((MyApplication) getApplicationContext()).getAppPrefs();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "checkLocationPermission: permission one");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionAlert();
                Log.e(TAG, "checkLocationPermission: permission two");
            } else {
                ActivityCompat.requestPermissions(this, permissions, CHECK_PERMISSIONS);
                Log.e(TAG, "checkLocationPermission: permission three");
            }
        } else {
            setUpNextActivity();
        }
    }

    private void showPermissionAlert() {
        Log.e(TAG, "checkLocationPermission: permission four");
        ActivityCompat.requestPermissions(this, permissions, CHECK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CHECK_PERMISSIONS) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == 0) {
                    Toast.makeText(this, permissions[i] + " permission not provided", Toast.LENGTH_SHORT).show();
                }
            }

            //showPermissionAlert();
            setUpNextActivity();
        }
    }

    private void setUpNextActivity() {
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
        },2000);
    }
}