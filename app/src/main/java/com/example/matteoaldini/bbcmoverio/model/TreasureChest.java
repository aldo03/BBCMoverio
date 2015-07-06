package com.example.matteoaldini.bbcmoverio.model;

/**
 * Created by matteo.aldini on 03/07/2015.
 */
public class TreasureChest {
    private int number;
    private double latitude;
    private double longitude;
    private int money;
    public enum State{
        UNVISITED, OPEN, LOCKED_KEY, LOCKED_COOPERATION, FINAL;
    }
    State state;


    public TreasureChest(int number, double latitude, double longitude, int money, int state) {
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.money = money;
        switch (state){
            case 0:
                this.state = State.UNVISITED;
                break;
            case 1:
                this.state = State.OPEN;
                break;
            case 2:
                this.state = State.LOCKED_KEY;
                break;
            case 3:
                this.state = State.LOCKED_COOPERATION;
                break;
            case 4:
                this.state = State.FINAL;
                break;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
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
