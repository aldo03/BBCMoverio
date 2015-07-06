package com.example.matteoaldini.bbcmoverio.model;

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
            if(t.getNumber() == treasureChest.getNumber()){
                treasureChests.set(i,treasureChest);
            }
            i++;
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
        this.maxPoints-=amount;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public void dimPoints(int points){
        this.points-=points;
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
}
