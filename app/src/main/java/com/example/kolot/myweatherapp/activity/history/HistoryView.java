package com.example.kolot.myweatherapp.activity.history;

import com.example.kolot.myweatherapp.activity.database.DbDto;

import java.util.List;

/**
 * Created by kolot on 05.04.2018.
 */

public interface HistoryView {
    void loadList();
    void deleteItems(int size, List<DbDto> dbDtos);
}
