package com.frndzcode.task_webskitters.view.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.frndzcode.task_webskitters.model.UserModel;
import com.frndzcode.task_webskitters.view.dao.UserDao;
import com.frndzcode.task_webskitters.view.database.AppDatabase;
import com.frndzcode.task_webskitters.view.database.DatabaseClient;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<UserModel>> userLiveData;
    private MutableLiveData<Boolean> savedStatus;
    private Application application;

    public UserRepository(Application application) {
        this.application = application;
        AppDatabase database = DatabaseClient.getInstance(application).getAppDatabase();
        userDao = database.userDao();
        userLiveData = userDao.getAllUser();

        savedStatus = new MutableLiveData<>();
    }

    public void insert(UserModel model) {
        new InsertUserAsyncTask(userDao, application).execute(model);
        savedStatus.postValue(true);
    }

    public MutableLiveData<Boolean> getSavedStatus() {
        return savedStatus;
    }

    public void update(UserModel model) {
        new UpdateUserAsyncTask(userDao).execute(model);
    }

    public void delete(UserModel model) {
        new DeleteUserAsyncTask(userDao).execute(model);
    }

    public LiveData<List<UserModel>> getAllUser() {
        return userLiveData;
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDao userDao;
        private Application application;

        private InsertUserAsyncTask(UserDao userDao, Application application) {
            this.userDao = userDao;
            this.application = application;
        }

        @Override
        protected Void doInBackground(UserModel... userModels) {
            userDao.insert(userModels[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(application, "Successfully saved user", Toast.LENGTH_SHORT).show();
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserModel... userModels) {
            userDao.update(userModels[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<UserModel, Void, Void> {

        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserModel... userModels) {
            userDao.delete(userModels[0]);
            return null;
        }
    }
}
