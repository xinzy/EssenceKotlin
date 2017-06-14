package com.xinzy.essence.kotlin.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by xinzy on 17/6/14.
 */

class HttpUtil {


    companion object {
        val sOkHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()
        val sRxRetrofit: Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(BASE_URL).build()

        fun getClient() = sOkHttpClient

        fun getRetrofit() = sRxRetrofit
    }
}