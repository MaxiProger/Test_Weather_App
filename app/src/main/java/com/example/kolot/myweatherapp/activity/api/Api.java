package com.example.kolot.myweatherapp.activity.api;

import com.example.kolot.myweatherapp.activity.dto.geoDto.ResponseGeoGoogle;
import com.example.kolot.myweatherapp.activity.dto.weatherDto.ResponseWeatherDto;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kolot on 05.04.2018.
 */

public interface Api {
    @GET("json")
    Observable<ResponseGeoGoogle> loadGeo(
            @Query("latlng") String latlng,
            @Query("key") String key,
            @Query("language") String lng);

    @GET("weather")
    Observable<ResponseWeatherDto> loadWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("lang") String lang,
            @Query("units") String units,
            @Query("appid") String appid
    );
}
