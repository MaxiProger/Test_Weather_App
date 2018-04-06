package com.example.kolot.myweatherapp.activity.dto.weatherDto;

import java.util.ArrayList;

/**
 * Created by kolot on 05.04.2018.
 */

public class ResponseWeatherDto {

    private ArrayList<WeatherDto> weather;

    private String description;
    private String icon;
    private String coordinates;

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    private MainDto main;
    private int visibility;
    private CloudsDto clouds;
    private SysDto sys;
    private String name;

    private CoordDto coordDto;

    public CoordDto getCoordDto() {
        return coordDto;
    }

    public void setCoordDto(CoordDto coordDto) {
        this.coordDto = coordDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<WeatherDto> getWeather() {
        return weather;
    }

    public MainDto getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public CloudsDto getClouds() {
        return clouds;
    }

    public SysDto getSys() {
        return sys;
    }

    public String getName() {
        return name;
    }


    public void setWeather(ArrayList<WeatherDto> weather) {
        this.weather = weather;
    }

    public void setMain(MainDto main) {
        this.main = main;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setClouds(CloudsDto clouds) {
        this.clouds = clouds;
    }

    public void setSys(SysDto sys) {
        this.sys = sys;
    }

    public void setName(String name) {
        this.name = name;
    }
}
