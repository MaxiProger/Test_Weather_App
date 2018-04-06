package com.example.kolot.myweatherapp.activity.helpers;

import com.example.kolot.myweatherapp.activity.database.Dao;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.example.kolot.myweatherapp.activity.database.MyApplication;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by kolot on 07.04.2018.
 */

public class CallableDeleteDao implements Callable<Integer> {

    private Dao dao = MyApplication.getInstance().getMyDataBase().dao();
    private List<DbDto> dbDto;

    public CallableDeleteDao(List<DbDto> dbDto) {
        this.dbDto = dbDto;
    }

    public int deleteCallable() {
        return dao.delete(this.dbDto);
    }


    @Override
    public Integer call() throws Exception {
        return deleteCallable();
    }
}
