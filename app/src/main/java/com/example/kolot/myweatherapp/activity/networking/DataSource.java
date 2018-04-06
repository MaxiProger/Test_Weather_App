package com.example.kolot.myweatherapp.activity.networking;

import com.example.kolot.myweatherapp.activity.api.Api;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.example.kolot.myweatherapp.activity.dto.geoDto.ResponseGeoGoogle;
import com.example.kolot.myweatherapp.activity.dto.weatherDto.ResponseWeatherDto;
import com.example.kolot.myweatherapp.activity.helpers.CallableDao;
import com.example.kolot.myweatherapp.activity.helpers.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kolot on 05.04.2018.
 */

public class DataSource  {
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String SCO = "latlong";
    private final String FORMAT = "json";
    private final String API_KEY_GOOGLE = "AIzaSyCe7xlRkGKTFyXNBL8a_CMjn8lvrSJLoyY";
    private final String LANGUAGE = "ru";
    private final String API_KEY_WEATHER = "59fa177e5bdb6187c8a3d4ec16ff6a7d";
    private final String UNITS = "metric";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    public interface DataInteractor {
        void onSuccess(long id);

        void onError(String s);
    }

    public Disposable getDisposable() {
        return this.disposable;
    }

    public void getData(final DataInteractor dataInteractor,
                        String latlng,
                        final double lat,
                        final double lon) {

        Api geoApiapi = new RetrofitHelper().createGeoApi();
        Api wethapi = new RetrofitHelper().createWeatherApi();

       disposable.add(

               Observable.zip(

                wethapi.loadWeather(lat, lon, LANGUAGE, UNITS, API_KEY_WEATHER),
                geoApiapi.loadGeo(latlng, API_KEY_GOOGLE, LANGUAGE), new BiFunction<ResponseWeatherDto, ResponseGeoGoogle, DbDto>() {
                    @Override
                    public DbDto apply(ResponseWeatherDto responseWeatherDto, ResponseGeoGoogle responseGeoGoogle) throws Exception {
                        DbDto dbDto = new DbDto();

                        dbDto.setAddress(responseGeoGoogle.getResults().get(0).getFormatted_address());
                        dbDto.setLat(lat);
                        dbDto.setLon(lon);
                        dbDto.setWeather(responseWeatherDto.getWeather().get(0).getDescription()+", "
                                +responseWeatherDto.getMain().getTemp()+ "°C");
                        dbDto.setIcon(responseWeatherDto.getWeather().get(0).getIcon());
                        dbDto.setTime(java.util.Calendar.getInstance().getTimeInMillis());
                        dbDto.setDate(dateFormat.format(new Date()));
                        dbDto.setFormattedTime(timeFormat.format(new Date()));

                        return dbDto;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DbDto>() {
                    @Override
                    public void accept(DbDto dbDto) throws Exception {

                        Observable.fromCallable(new CallableDao(dbDto))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        dataInteractor.onSuccess(aLong);

                                    }
                                });

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dataInteractor.onError(throwable.getMessage());
                    }
                }));

    }
}
