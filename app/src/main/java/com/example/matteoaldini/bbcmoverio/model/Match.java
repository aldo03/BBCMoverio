package com.example.matteoaldini.bbcmoverio.model;

import android.util.Log;

import java.util.List;
import java.util.Set;

/**
 * Created by matteo.aldini on 03/07/2015.
 */
public class Match {
    private List<TreasureChest> treasureChests;
    private int points;
    private int maxPoints;

    public Match(int points, int maxPoints) {
        this.points=points;
        this.maxPoints=maxPoints;
    }


    public void setTreasureChests(List<TreasureChest> treasureChests) {
        this.treasureChests = treasureChests;
    }

    public String updateTreasureChest(TreasureChest treasureChest){
        int i = 0;
        for(TreasureChest t : treasureChests){
            if(t.getLatitude() == treasureChest.getLatitude()
                    &&t.getLongitude() == treasureChest.getLongitude()){
                treasureChests.set(i, treasureChest);
                Log.i("STATE", treasureChest.getState().toString());
                return this.getMessageFromUpdate(treasureChest);
            }
            i++;
        }
        return "FAIL";
    }

    private String getMessageFromUpdate(TreasureChest treasureChest) {
        switch (treasureChest.state){
            case OPEN:
                this.points+=treasureChest.getMoney();
                return "Treasure n."+treasureChest.getNumber()+" opened!!! "+treasureChest.getMoney()+" earned!!";
            case LOCKED_KEY:
                return "Treasure n."+treasureChest.getNumber()+" needs key"+treasureChest.getNumber()+"!!!";
            case LOCKED_COOPERATION:
                return "Treasure n."+treasureChest.getNumber()+" needs cooperation. Wait until your friend asks your request";
            case FINAL:
                return "You have finished the game!!!";
        }
        return null;
    }

    public void setMaxPoints(int maxPoints){
        this.maxPoints=maxPoints;
    }

    public void setPoints(int points){
        this.points=points;
    }

    public void dimMaxPoints(int amount){
        this.maxPoints=amount;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public List<TreasureChest> getTreasures(){
        return this.treasureChests;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getPoints() {
        return points;
    }

    public String updateTreasureChestNotPresent(TreasureChest treasureChest) {
        int i = 0;
        for(TreasureChest t : treasureChests){
            if(t.getLatitude() == treasureChest.getLatitude()
                    &&t.getLongitude() == treasureChest.getLongitude()){
                treasureChests.set(i, treasureChest);
                Log.i("STATE", treasureChest.getState().toString());
                return this.getMessageFromUpdateNotPresent(treasureChest);
            }
            i++;
        }
        return "FAIL";
    }

    private String getMessageFromUpdateNotPresent(TreasureChest treasureChest) {
        switch (treasureChest.state){
            case OPEN:
                this.points+=treasureChest.getMoney();
                return "Treasure n."+treasureChest.getNumber()+" opened by your friend!!! "+treasureChest.getMoney()+" earned!!";
            case LOCKED_KEY:
                return "Treasure n."+treasureChest.getNumber()+" found by your friend, needs key"+treasureChest.getNumber()+"!!!";
            case LOCKED_COOPERATION:
                return "COOPERATION";
            case FINAL:
                return "You have finished the game!!!";
        }
        return null;
    }
}
