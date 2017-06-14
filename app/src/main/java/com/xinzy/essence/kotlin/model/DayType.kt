package com.xinzy.essence.kotlin.model

/**
 * Created by Xinzy on 2017/6/14.
 */

class DayType {
    var error: Boolean = false
    var category: List<String> = listOf()
    var results: Map<String, List<Essence>> = mapOf()
}
