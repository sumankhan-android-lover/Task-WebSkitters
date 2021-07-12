package com.frndzcode.task_webskitters.view.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.frndzcode.task_webskitters.model.UserModel;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user order by name desc")
    LiveData<List<UserModel>> getAllUser();

    @Insert
    void insert(UserModel model);

    @Delete
    void delete(UserModel model);

    @Update
    void update(UserModel model);

    @Query("DELETE FROM user")
    void deleteAllUser();
}
