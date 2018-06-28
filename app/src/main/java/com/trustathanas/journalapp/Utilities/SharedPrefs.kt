package com.trustathanas.journalapp.Utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    val PREFERENCE_FILENAME = "preferences"
    val prefs = context.getSharedPreferences(PREFERENCE_FILENAME, 0)

    val IS_LOGGED_IN = "isLoggedIn"
    val USER_DISPLAYNAME = "displayName"
    val USER_FAMILYNAME = "familyName"
    val USER_EMAIL = "email"


    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var displayName: String
        get() = prefs.getString(USER_DISPLAYNAME, "")
        set(value) = prefs.edit().putString(USER_DISPLAYNAME, value).apply()

    var familyName: String
        get() = prefs.getString(USER_FAMILYNAME, "")
        set(value) = prefs.edit().putString(USER_FAMILYNAME, value).apply()

    var userEmail: String
        get() = prefs.getString(USER_EMAIL, "")
        set(value) = prefs.edit().putString(USER_EMAIL, value).apply()

}