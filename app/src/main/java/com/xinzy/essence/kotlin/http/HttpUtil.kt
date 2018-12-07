package com.xinzy.essence.kotlin.http

import com.google.gson.GsonBuilder
import com.xinzy.essence.kotlin.util.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by xinzy on 17/6/14.
 */

class HttpUtil {


    companion object {

        private var sRxRetrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {
            if (sRxRetrofit == null) {
                val client: OkHttpClient = Utils.httpClientBuilder().build()
                val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create()

                sRxRetrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                       .client(client).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(BASE_URL).build()
            }

            return sRxRetrofit!!
        }
    }

}