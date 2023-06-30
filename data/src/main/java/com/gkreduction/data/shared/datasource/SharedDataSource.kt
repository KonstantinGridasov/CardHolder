package com.gkreduction.data.shared.datasource

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Observable

class SharedDataSource(val context: Context) {
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        const val SHARED_BASE =
            "com.gkreduction.games.data.repository.shared.datasource.SHARED_BASE"
        const val USER_THEME =
            "com.gkreduction.data.shared.datasource.USER_THEME"

    }

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_BASE, Context.MODE_PRIVATE)
    }


    fun getTheme(): Observable<Int> {
        return Observable.just(getInt(USER_THEME))

    }

    fun saveTheme(theme: Int?): Observable<Boolean> {
        if (theme != null)
            putInt(USER_THEME, theme)
        return Observable.just(true)
    }


    private fun getInt(key: String): Int {
        return sharedPreferences!!.getInt(key, 0)
    }

    private fun putInt(key: String, value: Int) {
        val editor = sharedPreferences!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, defaultValue)
    }

    private fun putBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return sharedPreferences!!.getString(key, "")!!
    }

    private fun putString(key: String, mStrings: String) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, mStrings)
        editor.apply()
    }


}