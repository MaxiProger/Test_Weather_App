package com.example.kolot.myweatherapp.activity.online;

/**
 * Created by kolot on 05.04.2018.
 */

public interface OnlineView {
    void showMessage(String s);
    double getLat();
    double getLon();
    void refresh();
    void showWeather(long s);
    void getCoordinates();
    String getlatlng();
}
