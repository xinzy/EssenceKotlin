package com.xinzy.essence.kotlin.api

import com.xinzy.essence.kotlin.util.EssenceException

/**
 * Created by Xinzy on 2017/6/14.
 */
interface ApiCallback<T> {
    fun onStart()

    fun onSuccess(data: T)

    fun onFailure(e: EssenceException)
}