package com.pablosanchezegido.petcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private static final String FIRST_TIME_LAUNCHED = "firstTimeLaunched";
    private static final String LED_COLOR = "ledColor";
    private static final String SEARCH_RADIUS = "searchRadius";
    private static final String NUM_MAX_RECENT_ACTIVITY = "numMaxRecentActivity";

    private static final float DEFAULT_SEARCH_RADIUS = 1000;  // kilometers
    private static final int DEFAULT_NUM_MAX_RECENT_ACTIVITY = 10;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public boolean isFirstTimeLaunched() {
        return preferences.getBoolean(FIRST_TIME_LAUNCHED, true);
    }

    public void setIsFirstTimeLaunched(boolean isFirstTimeLaunched) {
        editor.putBoolean(FIRST_TIME_LAUNCHED, isFirstTimeLaunched);
        editor.apply();
    }

    public void saveSettings(int color, float radius, int numMax) {
        editor.putInt(LED_COLOR, color);
        editor.putFloat(SEARCH_RADIUS, radius);
        editor.putInt(NUM_MAX_RECENT_ACTIVITY, numMax);
        editor.apply();
    }

    public int getLedColor() {
        return preferences.getInt(LED_COLOR, Color.parseColor("#2196F3"));  // Primary color
    }

    public float getSearchRadius() {
        return preferences.getFloat(SEARCH_RADIUS, DEFAULT_SEARCH_RADIUS);
    }

    public int getNumMaxRecentActivity() {
        return preferences.getInt(NUM_MAX_RECENT_ACTIVITY, DEFAULT_NUM_MAX_RECENT_ACTIVITY);
    }
}
