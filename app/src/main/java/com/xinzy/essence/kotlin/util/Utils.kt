package com.xinzy.essence.kotlin.util

import java.util.*


/**
 * Created by Xinzy on 2017/6/15.
 */

object Utils {

    fun format(date: Date): String {
        return format(date.getTime())
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
}
