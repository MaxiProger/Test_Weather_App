package com.example.kolot.myweatherapp.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kolot.myweatherapp.R;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.google.gson.Gson;

public class DetailInfoActivity extends AppCompatActivity {

    private DbDto dbDto;
    private TextView weather;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        weather = (TextView) findViewById(R.id.detail_weather);
        image = (ImageView) findViewById(R.id.detail_image);


        Intent intent = getIntent();
        String jsonDbDto = intent.getStringExtra("dbdto");
        dbDto = new Gson().fromJson(jsonDbDto, DbDto.class);


        int id = getResources().getIdentifier("drawable/i"+dbDto.getIcon(), null, getPackageName());

        image.setImageDrawable(getResources().getDrawable(id));

        weather.setText(dbDto.toString());

        Log.e("weather", dbDto.getWeather());
    }
}
