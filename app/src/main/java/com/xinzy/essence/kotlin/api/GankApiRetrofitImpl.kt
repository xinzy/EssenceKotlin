package com.xinzy.essence.kotlin.api

import com.xinzy.essence.kotlin.api.retrofit.GankService
import com.xinzy.essence.kotlin.http.HttpUtil
import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.model.ListSimple
import com.xinzy.essence.kotlin.util.EssenceException
import com.xinzy.essence.kotlin.util.L
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by xinzy on 17/6/14.
 */
class GankApiRetrofitImpl : GankApi {

    override fun category(category: String, count: Int, page: Int, callback: ApiCallback<List<Essence>>?) {

        callback?.onStart()

        val service: GankService = HttpUtil.getRetrofit().create(GankService::class.java)
        val call: Call<ListSimple<Essence>> = service.category(category, count, page)
        call.enqueue(object: Callback<ListSimple<Essence>> {
            override fun onFailure(call: Call<ListSimple<Essence>>?, t: Throwable?) {
                L.w("request failure")
                callback?.onFailure(EssenceException(t))
            }

            override fun onResponse(call: Call<ListSimple<Essence>>?, response: Response<ListSimple<Essence>>?) {
                val data: List<Essence>? = response?.body()?.results
                L.d("request success")
                callback?.onSuccess(data)
            }
        })
    }

    override fun day(year: Int, month: Int, day: Int, callback: ApiCallback<DayType>?) {

        callback?.onStart()

        val service: GankService = HttpUtil.getRetrofit().create(GankService::class.java)
        val call = service.day(year, month, day)
        call.enqueue(object: Callback<DayType> {
            override fun onFailure(call: Call<DayType>?, t: Throwable?) {
                callback?.onFailure(EssenceException(t))
            }

            override fun onResponse(call: Call<DayType>?, response: Response<DayType>?) {
                val data = response?.body()
                callback?.onSuccess(data)
            }
        })
    }
}