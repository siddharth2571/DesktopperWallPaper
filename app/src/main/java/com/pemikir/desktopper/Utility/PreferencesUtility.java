/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.pemikir.desktopper.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import com.pemikir.desktopper.R;

public final class PreferencesUtility {

    private static PreferencesUtility sInstance;

    public static final String FOLDER_NAME = "FOLDER_NAME";
    public static final String SELECTED_ROW = "selected_row";
    public static final String PREF_NAME = "Wallpaper";
    public static int PRIVATE_MODE = 0;


    private static SharedPreferences.Editor editor;

    private static SharedPreferences mPreferences;

    public PreferencesUtility(final Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mPreferences.edit();
    }

    public static final PreferencesUtility getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesUtility(context.getApplicationContext());
        }
        return sInstance;
    }


    public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public String getFolderName() {
        return mPreferences.getString(FOLDER_NAME, "Wallpaper");
    }

    public void setFolderName(String foldername) {
        editor.putString(FOLDER_NAME, foldername);
        editor.commit();
    }

    public int getNo_of_rows() {
        return mPreferences.getInt(SELECTED_ROW, 2);
    }

    public void setNo_of_rows(int selected_row) {
        editor.putInt(SELECTED_ROW, selected_row);
        editor.commit();
    }



}