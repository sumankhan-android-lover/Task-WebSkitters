package com.frndzcode.task_webskitters.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.frndzcode.task_webskitters.model.UserModel;
import com.frndzcode.task_webskitters.view.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<UserModel>> userLiveData;
    private MutableLiveData<Boolean> savedStatus;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        userLiveData = repository.getAllUser();
        savedStatus = repository.getSavedStatus();
    }

    public void insertUser(UserModel model){
        repository.insert(model);
    }

    public MutableLiveData<Boolean> getSavedStatus() {
        return savedStatus;
    }

    public void updateUser(UserModel model){
        repository.update(model);
    }

    public void deleteUser(UserModel model){
        repository.delete(model);
    }

    public LiveData<List<UserModel>> getUserLiveData() {
        return userLiveData;
    }
}
