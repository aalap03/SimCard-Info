package com.example.aalap.simcardinfo.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.aalap.simcardinfo.R

class Preference(var appContext: Context) {
    var THEME = "Theme"
    var sharedPreference: SharedPreferences = appContext.getSharedPreferences("Sim-Info", MODE_PRIVATE)

    private fun setTheme(themeRes: Int) {
        sharedPreference.edit().putInt(THEME, themeRes).apply()
    }

    fun getTheme(): Int{
        return sharedPreference.getInt(THEME, R.style.AppTheme)
    }

    fun changeTheme() {
        if(getTheme() == R.style.AppTheme)
            setTheme(R.style.AppTheme_Dark)
        else
            setTheme(R.style.AppTheme)
    }
}