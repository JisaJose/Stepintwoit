package com.jisa.stepintwoit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jisajose on 2018-01-12.
 */

public class SharedpreferenceUtils {
    Context mContext;

    public SharedpreferenceUtils(Context context) {
        mContext = context;

    }

    public void store(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String value = sharedPreferences.getString(key, null);
        return value;
    }
}
