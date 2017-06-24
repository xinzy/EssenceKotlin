package com.xinzy.essence.kotlin.model

import android.util.ArrayMap

/**
 * Created by Xinzy on 2017/6/14.
 */

class DayType {
    var error: Boolean = false
    var category: List<String> = listOf()
    var results: ArrayMap<String, List<Essence>> = ArrayMap()

    fun list() : MutableList<Any> {
        val list = mutableListOf<Any>()
        if (!error && results.isNotEmpty()) {
            for ((key, value) in results) {
                list.add(key)
                value?.let { list.addAll(value) }
            }
        }

        return list
    }
}
