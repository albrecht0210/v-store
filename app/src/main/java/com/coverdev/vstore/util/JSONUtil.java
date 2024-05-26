package com.coverdev.vstore.util;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class JSONUtil {
    public static JSONArray readJSON(Context context, String jsonFileName) {
        AssetManager assetManager = context.getAssets();
        InputStream input;
        String json = null;

        JSONArray jsonArray = null;
        try {
            input = assetManager.open(jsonFileName);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException jex) {
            jex.printStackTrace();
        }

        return jsonArray;
    }
}
