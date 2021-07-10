package com.frndzcode.task_webskitters;

import android.app.Application;
import android.content.SharedPreferences;

import com.androidnetworking.AndroidNetworking;

public class MyApplication extends Application {

    private SharedPreferences appPrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        appPrefs = getSharedPreferences("AppPrefs",MODE_PRIVATE);
        AndroidNetworking.initialize(getApplicationContext());
    }

    public SharedPreferences getAppPrefs() {
        return appPrefs;
    }

    public void setAppPrefs(SharedPreferences appPrefs) {
        this.appPrefs = appPrefs;
    }
}
