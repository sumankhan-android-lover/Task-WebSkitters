package com.frndzcode.task_webskitters.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.FragmentAddUserBinding;
import com.frndzcode.task_webskitters.model.UserModel;
import com.frndzcode.task_webskitters.utils.Validation;
import com.frndzcode.task_webskitters.view.interfaces.ImageCapturedListener;
import com.frndzcode.task_webskitters.viewModel.UserViewModel;

public class AddUserFragment extends Fragment /*implements ImageCapturedListener */ {

    private FragmentAddUserBinding binding;
    private ImageCapturedListener capturedListener;
    private AppCompatActivity activity;
    private UserViewModel viewModel;

    public AddUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        capturedListener = (ImageCapturedListener) getActivity();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindActivity(view);
        initListener();
    }

    private void bindActivity(View view) {
        activity = (AppCompatActivity) getActivity();
        if (activity != null)
            viewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        viewModel.getSavedStatus().observe(activity, aBoolean ->{
                    //Navigation.findNavController(view).popBackStack(R.id.userFragment,true)
                }
        );
    }

    private void initListener() {
        binding.submit.setOnClickListener(v -> {
            if (verifyLayout()){
                UserModel model = new UserModel();
                model.setName(binding.name.getText().toString());
                model.setEmail(binding.email.getText().toString());
                model.setPhone(binding.phone.getText().toString());
                model.setAddress(binding.address.getText().toString());

                viewModel.insertUser(model);

                Navigation.findNavController(v).popBackStack(R.id.userFragment,true);

            }
        });
    }

    private boolean verifyLayout() {
        if (TextUtils.isEmpty(binding.name.getText())){
            Toast.makeText(activity, "Please enter name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (Validation.isStringEmpty(binding.email.getText().toString())){
            Toast.makeText(activity, "Please enter your email", Toast.LENGTH_SHORT).show();
            return false;
        }  else if (Validation.isStringEmpty(binding.phone.getText().toString())){
            Toast.makeText(activity, "Please enter phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.address.getText())){
            Toast.makeText(activity, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}