package com.pablosanchezegido.petcity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private final String FIRST_TIME_LAUNCHED = "firstTimeLaunched";

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
}
