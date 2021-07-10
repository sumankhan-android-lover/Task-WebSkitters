package com.frndzcode.task_webskitters.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.frndzcode.task_webskitters.MyApplication;
import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.ActivityIntroBinding;
import com.frndzcode.task_webskitters.model.IntroModel;
import com.frndzcode.task_webskitters.view.adapters.IntroPagerAdapter;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private IntroActivity activity;
    private SharedPreferences appPrefs;
    private ArrayList<IntroModel> introList;
    private IntroPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        bindActivity();
        setIntroData();
        setUpIndicator();
        initListener();
    }

    private void bindActivity() {
        appPrefs = ((MyApplication) getApplicationContext()).getAppPrefs();
        introList = new ArrayList<>();
    }

    private void setIntroData() {
        IntroModel intro1 = new IntroModel();
        intro1.setImage(R.drawable.first_intro);
        intro1.setTitle("Discuss");
        intro1.setDetails("This page will generate random lorem ipsum with convincing sentence and paragraphing constructs. Just fill in the fields below to suit your needs and a customised random load of pseudo-latin guff will be sent back to you.");
        introList.add(intro1);

        IntroModel intro2 = new IntroModel();
        intro2.setImage(R.drawable.second_intro);
        intro2.setTitle("Working");
        intro2.setDetails("This page will generate random lorem ipsum with convincing sentence and paragraphing constructs. Just fill in the fields below to suit your needs and a customised random load of pseudo-latin guff will be sent back to you.");
        introList.add(intro2);

        IntroModel intro3 = new IntroModel();
        intro3.setImage(R.drawable.third_intro);
        intro3.setTitle("Success");
        intro3.setDetails("This page will generate random lorem ipsum with convincing sentence and paragraphing constructs. Just fill in the fields below to suit your needs and a customised random load of pseudo-latin guff will be sent back to you.");
        introList.add(intro3);

        adapter = new IntroPagerAdapter(introList,activity);
        binding.viewpager.setAdapter(adapter);
    }

    private void setUpIndicator(){
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0;i<indicators.length;i++){
            indicators[i] = new ImageView(activity);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    activity,R.drawable.shape_indicator_deactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            binding.indicatorArea.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpCurrentIndicator(int index){
        if (index == adapter.getItemCount() -1){
            binding.next.setText("Finish");
        }else {
            binding.next.setText("Next");
        }

        int childCount = binding.indicatorArea.getChildCount();
        for (int i=0;i<childCount;i++){
            ImageView imageView = (ImageView) binding.indicatorArea.getChildAt(i);
            if (i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                activity,R.drawable.shape_indicator_active));
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                activity,R.drawable.shape_indicator_deactive));
            }
        }
    }

    private void initListener() {
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setUpCurrentIndicator(position);
            }
        });

        binding.next.setOnClickListener(v -> {
            if (binding.viewpager.getCurrentItem()+1 < adapter.getItemCount()){
                binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem()+1);
            }else {
                appPrefs.edit().putBoolean("intro_status",true).apply();
                Intent intent = new Intent(activity,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}