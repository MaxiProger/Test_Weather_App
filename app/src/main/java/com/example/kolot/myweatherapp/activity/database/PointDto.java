package com.example.kolot.myweatherapp.activity.database;

import com.example.kolot.myweatherapp.activity.dto.geoDto.ResponseGeoGoogle;
import com.example.kolot.myweatherapp.activity.dto.weatherDto.ResponseWeatherDto;

import java.util.Date;

/**
 * Created by kolot on 05.04.2018.
 */


public class PointDto {
    private ResponseWeatherDto responseWeatherDto;
    private ResponseGeoGoogle responseGeoGoogle;
    private Date date;

    public PointDto(ResponseWeatherDto responseWeatherDto, ResponseGeoGoogle responseGeoGoogle, Date date) {

        this.responseWeatherDto = responseWeatherDto;
        this.responseGeoGoogle = responseGeoGoogle;
        this.date = date;
    }

    public void setResponseWeatherDto(ResponseWeatherDto responseWeatherDto) {
        this.responseWeatherDto = responseWeatherDto;
    }

    public void setResponseGeoGoogle(ResponseGeoGoogle responseGeoGoogle) {
        this.responseGeoGoogle = responseGeoGoogle;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ResponseWeatherDto getResponseWeatherDto() {
        return responseWeatherDto;
    }

    public ResponseGeoGoogle getResponseGeoGoogle() {
        return responseGeoGoogle;
    }

    public Date getDate() {
        return date;
    }

}