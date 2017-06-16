package com.xinzy.essence.kotlin

import android.app.Application
import com.squareup.picasso.Picasso
import com.xinzy.essence.kotlin.http.PicassoDownloader

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
        Picasso.setSingletonInstance(Picasso.Builder(this).downloader(PicassoDownloader(this)).build())
    }

}