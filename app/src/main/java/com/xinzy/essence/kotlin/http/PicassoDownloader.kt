package com.xinzy.essence.kotlin.http

import android.net.Uri
import com.squareup.picasso.Downloader
import com.squareup.picasso.NetworkPolicy
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by Xinzy on 2017/6/15.
 */
class PicassoDownloader : Downloader {
    private val httpClient: OkHttpClient = HttpUtil.sOkHttpClient

    override fun load(uri: Uri?, networkPolicy: Int): Downloader.Response {
        val builder = CacheControl.Builder()
        if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
            builder.onlyIfCached()
        } else {
            if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                builder.noCache()
            }
            if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                builder.noStore()
            }
        }
        val request = Request.Builder().cacheControl(builder.build()).url(uri.toString()).build()
        val response = httpClient.newCall(request).execute()
        val body = response.body()
        return Downloader.Response(body!!.byteStream(), false, body.contentLength())
    }

    override fun shutdown() {

    }
}