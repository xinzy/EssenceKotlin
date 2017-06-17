package com.xinzy.essence.kotlin.api.retrofit

import com.xinzy.essence.kotlin.http.PATH_CATEGORY
import com.xinzy.essence.kotlin.http.PATH_DAY
import com.xinzy.essence.kotlin.http.PATH_SEARCH
import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.model.ListSimple
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Xinzy on 2017/6/14.
 */
interface GankService {
    /**
     * 分类数据

     * @param category
     * *
     * @param count
     * *
     * @param page
     * *
     * @return
     */
    @GET(PATH_CATEGORY)
    fun category(@Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Call<ListSimple<Essence>>

    /**
     * 每日数据
     * @param year
     * *
     * @param month
     * *
     * @param day
     * *
     * @return
     */
    @GET(PATH_DAY)
    fun day(@Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int): Call<DayType>

    /**
     * 搜索
     * @param keyword
     * *
     * @param category
     * *
     * @param count
     * *
     * @param page
     * *
     * @return
     */
    @GET(PATH_SEARCH)
    fun search(@Path("keyword") keyword: String, @Path("category") category: String, @Path("count") count: Int, @Path("page") page: Int): Call<ListSimple<Essence>>
}