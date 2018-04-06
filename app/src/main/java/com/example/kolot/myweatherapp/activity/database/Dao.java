package com.example.kolot.myweatherapp.activity.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by kolot on 06.04.2018.
 */
@android.arch.persistence.room.Dao
public interface Dao {

    @Query("select * from DbDto")
    Flowable<List<DbDto>> getAll();

    @Query("select * from DbDto where id = :id")
    Flowable<DbDto> getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DbDto dbDto);

    @Delete
    int delete(List<DbDto> dbDtos);
}
