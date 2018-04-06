package com.example.kolot.myweatherapp.activity.online;


import com.example.kolot.myweatherapp.activity.database.Dao;
import com.example.kolot.myweatherapp.activity.database.MyApplication;
import com.example.kolot.myweatherapp.activity.networking.DataSource;

/**
 * Created by kolot on 05.04.2018.
 */

public class OnlinePresenter implements DataSource.DataInteractor {

    private OnlineView onlineView;
    private DataSource dataSource = new DataSource();
    private Dao dao = MyApplication.getInstance().getMyDataBase().dao();


    public OnlinePresenter(OnlineView onlineView){
        this.onlineView = onlineView;
    }

    public void getData(){
        onlineView.getCoordinates();
        dataSource.getData(this,
                onlineView.getlatlng(),
                onlineView.getLat(),
                onlineView.getLon());
    }

    @Override
    public void onSuccess(long id) {
       onlineView.showWeather(id);
    }

    @Override
    public void onError(String s) {
        onlineView.showMessage(s);
    }

    public void onRefresh(){
        onlineView.refresh();
    }
}
