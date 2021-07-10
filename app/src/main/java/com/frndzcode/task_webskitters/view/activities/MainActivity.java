package com.frndzcode.task_webskitters.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
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

        //appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment,R.id.mapFragment,R.id.userFragment).build();
       // appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupWithNavController(binding.toolbar,navController,appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation,navController);
    }
}