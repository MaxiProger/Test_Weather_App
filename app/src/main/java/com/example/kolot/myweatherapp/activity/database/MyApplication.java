package com.example.kolot.myweatherapp.activity.database;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by kolot on 06.04.2018.
 */

public class MyApplication extends Application {
    public static MyApplication instance;
    private MyDataBase myDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        myDataBase = Room.databaseBuilder(this, MyDataBase.class, "newdatabase").fallbackToDestructiveMigration().build();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public MyDataBase getMyDataBase() {
        return myDataBase;
    }
}
