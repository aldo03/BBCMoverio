package com.example.matteoaldini.bbcmoverio.utils;

import com.example.matteoaldini.bbcmoverio.model.Match;
import com.example.matteoaldini.bbcmoverio.model.Position;
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
        jsonObject.getDouble("latitude"),
        jsonObject.getDouble("longitude"),
        jsonObject.getInt("money"), jsonObject.getString("state"));
        return treasureChest;
    }

    public static Match getMatchFromJSONObject(JSONObject jsonObject) throws JSONException {
        Match match = new Match(jsonObject.getInt("points"),
                jsonObject.getInt("maxPoints"));

        JSONArray treasureArray = jsonObject.getJSONArray("treasureChests");
        List<TreasureChest> treasureChestSet = new ArrayList<>();
        for(int i=0; i<treasureArray.length();i++){
            JSONObject object = treasureArray.getJSONObject(i);
            TreasureChest treasureChest = new TreasureChest(0, object.getDouble("latitude"), object.getDouble("longitude"), 0,"UNVISITED");
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

    public static Position getPosition(JSONObject jsonObject) throws JSONException {
        Position position = new Position(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude"));
        return position;
    }

    //SEND TO SMARTPHONE PARSERS

    public static JSONObject getResponseJSONObject(String toSend) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 1);
        jsonObject.put("response", toSend);

        return jsonObject;
    }

    public static JSONObject getAlertJSONObject(String msg) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", 2);
        jsonObject.put("message", msg);

        return jsonObject;
    }
}
