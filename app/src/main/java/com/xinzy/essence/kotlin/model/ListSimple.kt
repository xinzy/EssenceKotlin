package com.xinzy.essence.kotlin.model

import android.support.annotation.Keep


/**
 * Created by Xinzy on 2017/6/14.
 */
@Keep
class ListSimple<T> {
    var error: Boolean = false
    var results: List<T> = listOf()
}