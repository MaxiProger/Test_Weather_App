package com.example.kolot.myweatherapp.activity.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by kolot on 06.04.2018.
 */
@Entity
public class DbDto{
    private double lat;
    private double lon;
    private long time;
    private String date;
    private String address;
    private String weather;
    private String formattedTime;
    private String icon;
    @PrimaryKey(autoGenerate = true)
    private long id;

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return  "Погода: " + weather+
                "\nШирота: " + lat +
                "\nДолгота: " + lon +
                "\nДата запроса: " + date  +
                "\nАдрес запроса: " + address +
                "\nВремя запроса: " + formattedTime;
    }
}
