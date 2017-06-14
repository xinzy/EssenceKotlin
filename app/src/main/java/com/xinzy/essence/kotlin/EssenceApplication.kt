package com.xinzy.essence.kotlin

import android.app.Application

/**
 * Created by Xinzy on 2017/6/14.
 */
class EssenceApplication: Application() {

    companion object {
        private lateinit var sInstance: EssenceApplication

        fun instance() = sInstance
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }

}