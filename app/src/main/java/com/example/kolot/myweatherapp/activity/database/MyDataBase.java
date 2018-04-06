package com.example.kolot.myweatherapp.activity.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by kolot on 06.04.2018.
 */
@Database(entities = {DbDto.class}, version = 7, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase{
    public abstract Dao dao();
}
