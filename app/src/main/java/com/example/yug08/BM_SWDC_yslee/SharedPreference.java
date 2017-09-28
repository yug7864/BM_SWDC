package com.example.yug08.BM_SWDC_yslee;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yug08.BM_SWDC_yslee.Item.IoTItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yug08 on 2017-09-28.
 * 메인이 너무 길어져서 나눴음
 */

public class SharedPreference {

    public static  String PREFS_NAME = "PRODUCT_APP";
    public static  String FAVORITES = "IOTitem";

    private String ID;

    public SharedPreference(String ID) {
        super();
        this.ID = ID;
    }


    public void saveItems(Context context, ArrayList<IoTItem> items) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        sharedPreferences = context.getSharedPreferences(ID,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(items);
        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public ArrayList<IoTItem> getItems(Context context, ArrayList<IoTItem> items) {
        SharedPreferences sharedPreferences;
        List<IoTItem> ioTItems;

        sharedPreferences = context.getSharedPreferences(ID,Context.MODE_PRIVATE);

        if (sharedPreferences.contains(FAVORITES)) {
            String jsonFavorites = sharedPreferences.getString(FAVORITES, null);
            Gson gson = new Gson();
            IoTItem[] favoriteItems = gson.fromJson(jsonFavorites,IoTItem[].class);

            ioTItems = Arrays.asList(favoriteItems);
            ioTItems = new ArrayList<IoTItem>(ioTItems);
        } else{
            return null;
        }


        return (ArrayList<IoTItem>) ioTItems;
    }
}
