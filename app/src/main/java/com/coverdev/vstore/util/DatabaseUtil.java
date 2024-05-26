package com.coverdev.vstore.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {
    private static Boolean isVtuberInitialize = false, isMerchandiseInitialize = false;

    public static void initializeDatabase(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("VStorePreferences", Context.MODE_PRIVATE);
        boolean isDataUploaded = sharedPreferences.getBoolean("isDataUploaded", false);
        System.out.println("isDataUploaded: " + isDataUploaded);
        if (!isDataUploaded) {
            // Your JSON data
            JSONArray jsonArray = JSONUtil.readJSON(context, "vtuber.json");

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            initializeVtubers(context, db);
            initializeMerchandise(context, db);

            System.out.println("IsSuccessVtuber: " + (isVtuberInitialize));
            System.out.println("IsSuccessMerch: " + (isMerchandiseInitialize));

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isDataUploaded", true);
            editor.apply();
        }
    }

    private static void initializeVtubers(Context context, FirebaseFirestore db) {
        JSONArray jsonArray = JSONUtil.readJSON(context, "vtuber.json");
        for (int i = 0; i < jsonArray.length(); i++) {
            Map<String, Object> vtuber = null;
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                vtuber = new HashMap<>();
                vtuber.put("name", jsonObject.getString("name"));
                vtuber.put("branch", jsonObject.getString("branch"));
                vtuber.put("generation", jsonObject.getString("generation"));
                vtuber.put("imageUrl", jsonObject.getString("imageUrl"));
            } catch (JSONException ex) {
                ex.getStackTrace();
            }

            setDatabase(db, "vtubers", vtuber).addOnCompleteListener(task -> {
                isVtuberInitialize = true;
            });
        }
    }

    private static void initializeMerchandise(Context context, FirebaseFirestore db) {
        JSONArray jsonArray = JSONUtil.readJSON(context, "merchandise.json");
        for (int i = 0; i < jsonArray.length(); i++) {
            Map<String, Object> merchandise = null;
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                merchandise = new HashMap<>();
                merchandise.put("name", jsonObject.getString("name"));
                merchandise.put("category", jsonObject.getString("category"));
                merchandise.put("availability", jsonObject.getString("availability"));
                merchandise.put("price", jsonObject.getString("price"));
                merchandise.put("description", jsonObject.getString("description"));
                merchandise.put("keywords", jsonObject.getString("keywords"));
                merchandise.put("imageUrl", jsonObject.getString("imageUrl"));
            } catch (JSONException ex) {
                ex.getStackTrace();
            }

            setDatabase(db, "merchandises", merchandise).addOnCompleteListener(task -> {
                isMerchandiseInitialize = true;
            });
        }
    }

    private static Task<Boolean> setDatabase(FirebaseFirestore db, String collection, Map<String, Object> data) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        db.collection(collection)
                .add(data)
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });

        return taskCompletionSource.getTask();
    }
}
