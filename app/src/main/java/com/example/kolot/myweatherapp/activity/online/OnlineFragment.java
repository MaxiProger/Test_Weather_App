package com.example.kolot.myweatherapp.activity.online;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kolot.myweatherapp.R;
import com.example.kolot.myweatherapp.activity.database.Dao;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.example.kolot.myweatherapp.activity.database.MyApplication;
import com.example.kolot.myweatherapp.activity.database.MyDataBase;
import com.example.kolot.myweatherapp.activity.helpers.LocationHelper;
import com.example.kolot.myweatherapp.activity.networking.DataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class OnlineFragment extends Fragment implements OnlineView {

    private String[] coordinates;
    private TextView geo_tv, address_tv, weather_tv;
    private OnlinePresenter presenter;
    private DataSource dataSource;
    private MyDataBase myDataBase;
    private Dao dao;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;
    private SwipeRefreshLayout refreshLayout;
    private Disposable disposable ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_online, container, false);

        geo_tv = (TextView) view.findViewById(R.id.online_geo);
        address_tv = (TextView) view.findViewById(R.id.online_adress);
        weather_tv = (TextView) view.findViewById(R.id.online_weather);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.online_constraint);
        imageView = (ImageView) view.findViewById(R.id.online_imageView) ;
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.online_refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                presenter.onRefresh();
            }
        });

        myDataBase = MyApplication.getInstance().getMyDataBase();
        dao = myDataBase.dao();

        presenter = new OnlinePresenter(this);

        dataSource = new DataSource();
        presenter.getData();


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (disposable != null)
            disposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.dispose();
    }

    @Override
    public void getCoordinates() {
        LocationHelper locationHelper = new LocationHelper(getContext());
        coordinates = locationHelper.getLoaction().split("/", 2);
    }


    @Override
    public void showMessage(String s) {
        refreshLayout.setRefreshing(false);
        Snackbar.make(getView(), s, Snackbar.LENGTH_LONG).show();
    }



    @Override
    public double getLat() {
        return Double.parseDouble(coordinates[0]);
    }

    @Override
    public double getLon() {
        return Double.parseDouble(coordinates[1]);
    }

    @Override
    public void refresh() {
        presenter.getData();
    }


    @Override
    public void showWeather(long weather) {
        refreshLayout.setRefreshing(false);

        disposable = dao.getById(weather).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DbDto>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(DbDto dbDto) throws Exception {

                        weather_tv.setText(dbDto.getWeather());
                        geo_tv.setText("Широта: " + String.valueOf(dbDto.getLat()) + "\n"
                                + "Долгота: " + String.valueOf(dbDto.getLon()));
                        address_tv.setText(dbDto.getAddress());

                        int id = getResources().getIdentifier("drawable/i"+dbDto.getIcon(), null, getContext().getPackageName());

                        imageView.setImageDrawable(getResources().getDrawable(id));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Snackbar.make(constraintLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public String getlatlng() {
        return coordinates[0] + "," + coordinates[1];
    }


}
