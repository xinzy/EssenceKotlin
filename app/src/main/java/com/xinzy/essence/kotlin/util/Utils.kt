package com.xinzy.essence.kotlin.util

import com.xinzy.essence.kotlin.BuildConfig
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager


/**
 * Created by Xinzy on 2017/6/15.
 */

object Utils {

    fun format(date: Date): String {
        return format(date.time)
    }

    fun format(time: Long): String {
        val diffSecond = (System.currentTimeMillis() - time) / 1000

        if (diffSecond < 60) {
            return "刚刚"
        } else if (diffSecond < 60 * 60) {
            return (diffSecond / 60).toString() + "分前"
        } else if (diffSecond < 60 * 60 * 24) {
            return (diffSecond / 60 / 60).toString() + "小时前"
        } else if (diffSecond < 60 * 60 * 24 * 30) {
            return (diffSecond / 60 / 60 / 24).toString() + "天前"
        } else if (diffSecond < 60 * 60 * 24 * 365) {
            return (diffSecond / 60 / 60 / 24 / 30).toString() + "月前"
        }
        return (diffSecond / 60 / 60 / 24 / 365).toString() + "年前"
    }

    fun download(url: String, file: File) {
        val client = httpClientBuilder().build()
        val request = Request.Builder().url(url).get().build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {}

            override fun onResponse(call: Call?, response: Response?) {
                val inputSteam = response?.body()?.byteStream()
                inputSteam?.let {
                    val bufferSize = 8 * 1024
                    val buffer = ByteArray(bufferSize)
                    val fos = FileOutputStream(file)

                    var length: Int
                    do {
                        length = inputSteam.read(buffer, 0 , bufferSize)
                        if (length > 0) fos.write(buffer, 0, length)
                    } while (length > 0)

                    inputSteam.close()
                    fos.close()
                }
            }
        })
    }

    fun httpClientBuilder(): OkHttpClient.Builder {
        val manager = TrustAllManager()
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(manager), null)

        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.hostnameVerifier { _, _ -> true }.sslSocketFactory(sslContext.socketFactory, manager)
        }

        return builder

    }
}


private class TrustAllManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}