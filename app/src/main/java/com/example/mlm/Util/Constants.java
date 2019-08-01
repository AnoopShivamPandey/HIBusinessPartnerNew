package com.example.mlm.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Constants {

    public static final String PREF_KEY = "superApp";

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getSavedPreferences(Context context, String key, String defValue) {
        SharedPreferences sp = initializeSharedPreferences(context);
        return sp.getString(key, defValue);
    }


    private static SharedPreferences initializeSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_KEY, Context.MODE_PRIVATE);
    }

    public static boolean clearSavedPreferences(Context context, String key) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean clearAllSavedPreferences(Context context) {
        SharedPreferences sp = initializeSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit().clear();
        return editor.commit();
    }
}
