package com.trustathanas.journalapp.Utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private val PREFERENCE_FILENAME = "preferences"
    private val prefs = context.getSharedPreferences(PREFERENCE_FILENAME, Context.MODE_PRIVATE)

    private val IS_LOGGED_IN = "isLoggedIn"
    private val USER_DISPLAYNAME = "displayName"
    private val USER_FAMILYNAME = "familyName"
    private val USER_EMAIL = "email"
    private val DID_SHOW_PROMPT = "didShowPrompt"
    private val TERMS_AND_CONDITIONS = "accepted_terms_of_service"


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

    var tsAndCs: Boolean
        get() = prefs.getBoolean(TERMS_AND_CONDITIONS, false)
        set(value) = prefs.edit().putBoolean(TERMS_AND_CONDITIONS, value).apply()

    var didShowPrompt: Boolean
        get() = prefs.getBoolean(DID_SHOW_PROMPT, false)
        set(value) = prefs.edit().putBoolean(DID_SHOW_PROMPT, value).apply()

}