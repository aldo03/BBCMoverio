package com.example.matteoaldini.bbcmoverio.model;

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

    public double getDistance(Position position) {
        return Math
                .sqrt(Math.pow((position.getLatitude() - this.latitude), 2.0)
                        + Math.pow((position.getLongitude() - this.longitude),
                        2.0));
    }
}
