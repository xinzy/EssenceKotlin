package com.xinzy.essence.kotlin.api

import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by Xinzy on 2017/6/14.
 */
interface GankApi {

    fun category(category: String, count: Int, page: Int, callback: ApiCallback<List<Essence>>?)

    fun day(year: Int, month: Int, day: Int, callback: ApiCallback<DayType>?)

}