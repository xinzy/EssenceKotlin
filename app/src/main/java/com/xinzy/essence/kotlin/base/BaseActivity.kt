package com.xinzy.essence.kotlin.base

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate

/**
 * Created by Xinzy on 2017/6/14.
 */

open class BaseActivity: AppCompatActivity() {

    val KEY_NIGHT_MODE: String = "keyNightMode"

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(if (isNightMode()) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
    }

    protected fun isNightMode(): Boolean = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_NIGHT_MODE, false)

    protected fun setNightMode(nightMode: Boolean) = PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(KEY_NIGHT_MODE, nightMode).apply()
}
