package com.example.kolot.myweatherapp.activity.online;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
    private DbDto savedDto;
    private MyDataBase myDataBase;
    private Dao dao;
    private ImageView imageView;
    private ConstraintLayout constraintLayout;
    private Disposable disposable;
    private long lastTime = 0;
    private ProgressDialog progressDialog;
    private final long HALF_HOUR = 1800000;
    private double lat1, lat2, lon1, lon2, distance;
    private FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_online, container, false);
        geo_tv = (TextView) view.findViewById(R.id.online_geo);
        address_tv = (TextView) view.findViewById(R.id.online_adress);
        weather_tv = (TextView) view.findViewById(R.id.online_weather);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.online_constraint);
        imageView = (ImageView) view.findViewById(R.id.online_imageView);

        fab = (FloatingActionButton) view.findViewById(R.id.online_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRefresh();
            }
        });

        fab.show();


        myDataBase = MyApplication.getInstance().getMyDataBase();
        dao = myDataBase.dao();


        progressDialog = new ProgressDialog(getActivity());

        presenter = new OnlinePresenter(this);

        dataSource = new DataSource();
        presenter.getData();
        presenter.showDialog(true);

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
    public void onStop() {
        super.onStop();

    }

    @Override
    public void getCoordinates() {
        LocationHelper locationHelper = new LocationHelper(getContext());
        coordinates = locationHelper.getLoaction().split("/", 2);
    }

    @Override
    public void showDialog(boolean b) {
        progressDialog.setTitle("Загрузка ");
        progressDialog.setMessage("Пожалуйста, подождите");
        if (b) progressDialog.show();
        else progressDialog.dismiss();
    }


    @Override
    public void showMessage(String s) {
        Snackbar.make(constraintLayout, s, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isStateSaved()) {

        }
    }

    @Override
    public void refresh() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (lastTime != 0) {
            if ((calculateDistance() <= 50) || (calendar.getTimeInMillis() - lastTime <= HALF_HOUR)) {
                Snackbar.make(constraintLayout, R.string.not_changed, Snackbar.LENGTH_LONG).show();
            } else {
                presenter.getData();
                presenter.showDialog(true);
            }
        }
    }

    public double calculateDistance() {
        getCoordinates();

        lat2 = Math.toRadians(getLat());
        lon2 = Math.toRadians(getLon());
        double lat1rad, lon1rad;
        lat1rad = Math.toRadians(lat1);
        lon1rad = Math.toRadians(lon1);

        double delta_lat, delta_lon;

        delta_lat = lat2 - lat1rad;
        delta_lon = lon2 - lon1rad;

        double radius = 6372.797;
        double a = Math.sin(delta_lat / 2) * Math.sin(delta_lat / 2) + Math.cos(lat1rad) * Math.cos(lat2) * Math.sin(delta_lon / 2) * Math.sin(delta_lon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance = radius * c;

        return distance;
    }

    @Override
    public void showWeather(final long id) {

        presenter.showDialog(false);

        disposable = dao.getById(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DbDto>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(DbDto dbDto) throws Exception {
                        savedDto = dbDto;
                        bindViews(dbDto);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Snackbar.make(constraintLayout, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    public void bindViews(DbDto dbDto) {
        weather_tv.setText(dbDto.getWeather());
        geo_tv.setText("Широта: " + String.valueOf(dbDto.getLat()) + "\n"
                + "Долгота: " + String.valueOf(dbDto.getLon()));
        address_tv.setText(dbDto.getAddress());

        lastTime = dbDto.getTime();
        lat1 = dbDto.getLat();
        lon1 = dbDto.getLon();

        int id = getResources().getIdentifier("drawable/i" + dbDto.getIcon(), null, getContext().getPackageName());

        imageView.setImageDrawable(getResources().getDrawable(id));
    }

    @Override
    public String getlatlng() {
        return coordinates[0] + "," + coordinates[1];
    }

    @Override
    public double getLat() {
        return Double.parseDouble(coordinates[0]);
    }

    @Override
    public double getLon() {
        return Double.parseDouble(coordinates[1]);
    }


}
