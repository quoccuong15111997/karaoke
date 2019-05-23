package com.nqc.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nqc.tracuukaraoke.BuildConfig;

public class SharedPreferencesManager {
    private static final String PREF_FIRST_TIME_SETUP = BuildConfig.APPLICATION_ID + ".pref_first_time_setup";


    private static SharedPreferences sPreferences;

    private SharedPreferencesManager() {
    }

    public static void init(Context context) {
        if (sPreferences == null) {
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void setFirstTimeSetup(boolean isFirstTime) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(PREF_FIRST_TIME_SETUP, isFirstTime);
        editor.apply();
    }

    public static boolean isFirstTimeSetup() {
        return sPreferences.getBoolean(PREF_FIRST_TIME_SETUP, true);
    }

}
