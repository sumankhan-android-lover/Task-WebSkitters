package com.frndzcode.task_webskitters.view.activities;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ActivityMainBinding;
import com.frndzcode.task_webskitters.view.interfaces.ImageCapturedListener;

public class MainActivity extends AppCompatActivity implements ImageCapturedListener {
    private ActivityMainBinding binding;
    private NavHostFragment hostFragment;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bindActivity();
    }

    private void bindActivity() {
        hostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = hostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController);
    }

    @Override
    public void imageCaptured(Uri fileUri) {

    }

    @Override
    public void imageProcessed(String base64Image) {

    }
}