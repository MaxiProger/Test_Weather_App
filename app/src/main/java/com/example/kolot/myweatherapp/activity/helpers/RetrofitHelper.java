package com.example.kolot.myweatherapp.activity.helpers;

import com.example.kolot.myweatherapp.activity.api.Api;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kolot on 05.04.2018.
 */

public class RetrofitHelper {

    private final String BASE_URL_GEO = "https://maps.googleapis.com/maps/api/geocode/";
    private final String BASE_URL_WEATHER = "http://api.openweathermap.org/data/2.5/";

    public Retrofit createGeoBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_GEO)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public Retrofit createWeatherBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_WEATHER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    public Api createWeatherApi(){
        return createWeatherBuilder().create(Api.class);
    }

    public Api createGeoApi(){
        return createGeoBuilder().create(Api.class);
    }
}
