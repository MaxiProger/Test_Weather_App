package com.example.kolot.myweatherapp.activity.helpers;

import com.example.kolot.myweatherapp.activity.database.Dao;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.example.kolot.myweatherapp.activity.database.MyApplication;

import java.util.concurrent.Callable;

/**
 * Created by kolot on 06.04.2018.
 */

public class CallableDao implements Callable<Long> {

    private Dao dao = MyApplication.getInstance().getMyDataBase().dao();
    private DbDto dbDto;

    public CallableDao(DbDto dbDto) {
        this.dbDto = dbDto;
    }

    public long insertCallable(){
        return dao.insert(this.dbDto);
    }

    @Override
    public Long call() throws Exception {
        return insertCallable();
    }
}
