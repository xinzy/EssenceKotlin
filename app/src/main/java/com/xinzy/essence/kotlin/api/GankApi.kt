package com.xinzy.essence.kotlin.api

import android.support.annotation.Nullable
import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by Xinzy on 2017/6/14.
 */
interface GankApi {

    fun category(category: String, count: Int, page: Int, @Nullable callback: ApiCallback<List<Essence>>)

    fun day(year: Int, month: Int, day: Int, @Nullable callback: ApiCallback<DayType>)

}