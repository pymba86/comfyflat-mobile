package com.gitlab.pymba86.comfyflat.mobile.model.data.storage

import android.content.Context
import com.gitlab.pymba86.comfyflat.mobile.entity.Session
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class Prefs @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    //region auth
    private val AUTH_DATA = "auth_data"
    private val KEY_CURRENT_SESSION = "ad_current_session"
    private val KEY_SESSIONS = "ad_session"
    private val authPrefs by lazy { getSharedPreferences(AUTH_DATA) }

    var selectedSession: String?
        get() = authPrefs.getString(KEY_CURRENT_SESSION, null)
        set(value) {
            authPrefs.edit().putString(KEY_CURRENT_SESSION, value).apply()
        }

    private val sessionsTypeToken = object : TypeToken<HashMap<String, Session>>() {}.type
    var sessions: HashMap<String, Session>
        get() {
            return gson.fromJson(authPrefs.getString(KEY_SESSIONS, "[]"), sessionsTypeToken)
        }
        set(value) {
            authPrefs.edit().putString(KEY_SESSIONS, gson.toJson(value)).apply()
        }
    //endregion

    //region app
    private val APP_DATA = "app_data"
    private val KEY_FIRST_LAUNCH_TIME = "launch_ts"
    private val appPrefs by lazy { getSharedPreferences(APP_DATA) }

    var firstLaunchTimeStamp: Long?
        get() = appPrefs.getLong(KEY_FIRST_LAUNCH_TIME, 0).takeIf { it > 0 }
        set(value) {
            appPrefs.edit().putLong(KEY_FIRST_LAUNCH_TIME, value ?: 0).apply()
        }
    //endregion
}