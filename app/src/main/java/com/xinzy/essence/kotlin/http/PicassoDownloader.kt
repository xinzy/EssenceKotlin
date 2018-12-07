package com.xinzy.essence.kotlin.http

import android.content.Context
import android.net.Uri
import com.squareup.picasso.Downloader
import com.squareup.picasso.NetworkPolicy
import com.xinzy.essence.kotlin.util.Utils.httpClientBuilder
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

/**
 * Created by Xinzy on 2017/6/15.
 */
class PicassoDownloader(context: Context) : Downloader {
    private val cacheSize = 20 * 1024 * 1024L
    private val httpClient: OkHttpClient = httpClientBuilder().cache(Cache(File(context.externalCacheDir, "picasso"), cacheSize)).build()

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