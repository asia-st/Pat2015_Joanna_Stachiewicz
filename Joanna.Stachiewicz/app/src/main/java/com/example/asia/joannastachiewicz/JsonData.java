package com.example.asia.joannastachiewicz;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class JsonData {

    private static final String MAIN_JSON_ARRAY = "array";
    private static final String JSON_TITLE = "title";

    private JsonData(){}

    public static List<JSONObject> getJsonObject(String json) throws JSONException {
        JSONArray jsonArray = new JSONObject(json).getJSONArray(MAIN_JSON_ARRAY);
        List<JSONObject> JsonElementsList = new ArrayList<JSONObject>();

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getJSONObject(i).has(JSON_TITLE))
            JsonElementsList.add(jsonArray.getJSONObject(i));
        }

        return JsonElementsList;
    }
}
