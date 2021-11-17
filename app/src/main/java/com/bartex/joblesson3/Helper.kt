package com.bartex.joblesson3

import androidx.preference.PreferenceManager

class Helper(val app: App): IHelper {
    override fun getIsClick(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(app).getBoolean(Constants.IS_CLICK, false)
    }

    override fun saveIsClick(click: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(app).edit()
            .putBoolean(Constants.IS_CLICK, click)
            .apply()
    }
}