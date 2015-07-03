package com.example.matteoaldini.bbcmoverio.model;

/**
 * Created by lorenzo.righi7 on 03/07/2015.
 */
public class Position {
    private double latitude;
    private double longitude;

    public Position(double latitude, double longitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
