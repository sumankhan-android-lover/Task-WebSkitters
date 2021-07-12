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
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.frndzcode.task_webskitters.R;
import com.frndzcode.task_webskitters.databinding.FragmentUserBinding;
import com.frndzcode.task_webskitters.model.UserModel;
import com.frndzcode.task_webskitters.view.adapters.UserAdapter;
import com.frndzcode.task_webskitters.viewModel.UserViewModel;

import java.util.List;

public class UserFragment extends Fragment {
    private static final String TAG = UserFragment.class.getSimpleName();
    private FragmentUserBinding binding;
    private NavController navController;
    private AppCompatActivity activity;
    private UserViewModel viewModel;
    private UserAdapter adapter;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater,container,false);
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
        navController = Navigation.findNavController(view);
        if (activity != null)
            viewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        viewModel.getUserLiveData().observe(activity, this::setDataOnRecyclerView);
    }

    private void setDataOnRecyclerView(List<UserModel> userModels) {
        Log.e(TAG, "setDataOnRecyclerView: user list size =>"+userModels.size() );
        binding.recycler.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new UserAdapter(userModels, activity, UserFragment.this);
        binding.recycler.setAdapter(adapter);
    }

    private void initListener() {
        binding.addUserButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_userFragment_to_addUserFragment);
        });
    }

    public void OnDeleteUser(UserModel model) {
        viewModel.deleteUser(model);
    }

    public void OnUpdateUser(UserModel model) {
        NavDirections action = UserFragmentDirections.actionUserFragmentToAddUserFragment();
        navController.navigate(action);
    }
}