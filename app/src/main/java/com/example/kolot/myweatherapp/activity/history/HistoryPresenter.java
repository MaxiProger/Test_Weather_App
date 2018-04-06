package com.example.kolot.myweatherapp.activity.history;

import com.example.kolot.myweatherapp.activity.database.DbDto;

import java.util.List;

/**
 * Created by kolot on 05.04.2018.
 */

public class HistoryPresenter {

    private HistoryView historyView;

    public HistoryPresenter(HistoryView historyView){
        this.historyView = historyView;
    }

    public void getData(){
        historyView.loadList();
    }

    public void trimList(int size, List<DbDto> dbDtos){
        historyView.deleteItems(size, dbDtos);
    }
}
