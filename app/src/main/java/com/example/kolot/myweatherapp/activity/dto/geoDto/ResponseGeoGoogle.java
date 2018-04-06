package com.example.kolot.myweatherapp.activity.dto.geoDto;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kolot on 05.04.2018.
 */

public class ResponseGeoGoogle {

    private ArrayList<Results> results;

    private Date date;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
