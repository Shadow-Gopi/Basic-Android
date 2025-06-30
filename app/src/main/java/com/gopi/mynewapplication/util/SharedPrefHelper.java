package com.gopi.mynewapplication.util;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPrefHelper {  public static SharedPrefHelper sharedPrefHelper;
    private static Context applicationContext;
    private SharedPreferences sharedpreferences;

    private static final String SHARED_PREFERENCE_USER_NAME = "user_name";
    private static final String SHARED_PREFERENCE_DEFAULT_STRING = "";
    public static synchronized SharedPrefHelper getInstance(Context context) {
        //using application context is recommended.
        if (sharedPrefHelper == null) {
            applicationContext = context;
            sharedPrefHelper = new SharedPrefHelper();
        }
        return sharedPrefHelper;
    }

    public SharedPrefHelper() {
        createSharedPref();
    }

    private void createSharedPref() {
        String sharedPrefName = "SharedPreference";
        sharedpreferences = applicationContext.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    public String getUserName() {

        if (sharedpreferences == null)
            return SHARED_PREFERENCE_DEFAULT_STRING;

        return sharedpreferences.getString(SHARED_PREFERENCE_USER_NAME, SHARED_PREFERENCE_DEFAULT_STRING);

    }

    public void setUserName(String name) {

        if (sharedpreferences == null)
            createSharedPref();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(SHARED_PREFERENCE_USER_NAME, name);

        editor.apply();

    }

    public void clearPref() {
        sharedpreferences.edit().clear().apply();
    }
}
