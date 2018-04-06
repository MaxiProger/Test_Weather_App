package com.example.kolot.myweatherapp.activity.dto.weatherDto;

/**
 * Created by kolot on 05.04.2018.
 */

public class WeatherDto {
    private String description;
    private String icon;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}

