package com.example.mapapplication;

import com.google.android.gms.maps.model.LatLng;

public class Bin {


    public int id;
    public double lat;
    public double log;
    public String description;
    public float hue;

    public Bin()
    { }

    public Bin(int id,String description,float hue,double lat,double log)
    {
        this.id = id;
        this.description = description;
        this.hue = hue;
        this.lat = lat;
        this.log = log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
