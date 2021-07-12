package com.frndzcode.task_webskitters.view.database;

import android.content.Context;

import androidx.room.Room;

import static com.frndzcode.task_webskitters.view.database.AppDatabase.MIGRATION_2_3;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;
    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "WebSkitters.db")
                .addMigrations(MIGRATION_2_3)
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
