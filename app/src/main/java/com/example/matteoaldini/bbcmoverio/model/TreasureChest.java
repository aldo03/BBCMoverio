package com.example.matteoaldini.bbcmoverio.model;

/**
 * Created by matteo.aldini on 03/07/2015.
 */
public class TreasureChest {
    private int number;
    private long latitude;
    private long longitude;
    private int money;
    public enum State{
        UNVISITED, OPEN, LOCKED_KEY, LOCKED_COOPERATION, FINAL;
    }
    State state;


    public TreasureChest(int number, long latitude, long longitude, int money) {
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.money = money;
        this.state = State.UNVISITED;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
