package com.example.matteoaldini.bbcmoverio.utils;

import com.example.matteoaldini.bbcmoverio.model.Match;
import com.example.matteoaldini.bbcmoverio.model.TreasureChest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by matteo.aldini on 03/07/2015.
 */
public class ParserUtils {
    public static TreasureChest getTreasureChestFromJSONObject(JSONObject jsonObject) throws JSONException {
        TreasureChest treasureChest = new TreasureChest(jsonObject.getInt("number"),
        jsonObject.getLong("latitude"),
        jsonObject.getLong("longitude"),
        jsonObject.getInt("money"));
        return treasureChest;
    }

    public static Match getMatchFromJSONObject(JSONObject jsonObject) throws JSONException {
        Match match = new Match(jsonObject.getInt("points"),
                jsonObject.getInt("maxPoints"));

        JSONArray treasureArray = jsonObject.getJSONArray("treasureChests");
        List<TreasureChest> treasureChestSet = new ArrayList<>();
        for(int i=0; i<treasureArray.length();i++){
            JSONObject object = treasureArray.getJSONObject(i);
            TreasureChest treasureChest = new TreasureChest(0, object.getLong("latitude"), object.getLong("longitude"), 0);
            treasureChestSet.add(treasureChest);
        }
        match.setTreasureChests(treasureChestSet);

        return match;
    }
    public static int getMoneyTheft(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("amount");
    }

    public static int getNewAmount(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("amount");
    }

    public static boolean getConfirmedOrRefused(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getString("response").equals("OK")){
            return true;
        }else {
            return false;
        }
    }
}
