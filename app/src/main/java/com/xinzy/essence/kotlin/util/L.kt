package com.xinzy.essence.kotlin.util

import android.util.Log

/**
 * Created by xinzy on 17/6/14.
 */
object L {

    private val allowD = true
    private val allowE = true
    private val allowI = true
    private val allowW = true
    private val allowV = true
    private val allowWtf = true

    private fun generateTag(caller: StackTraceElement): String {
        var tag = "%s.%s(L:%d)"
        var callerClazzName = caller.className
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
        tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber)
        return tag
    }

    fun d(content: String) {
        if (!allowD) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.d(tag, content)
    }

    fun d(content: String, tr: Throwable) {
        if (!allowD) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.d(tag, content, tr)
    }

    fun e(content: String) {
        if (!allowE) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.e(tag, content)
    }

    fun e(content: String, tr: Throwable) {
        if (!allowE) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.e(tag, content, tr)
    }

    fun i(content: String) {
        if (!allowI) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.i(tag, content)
    }

    fun i(content: String, tr: Throwable) {
        if (!allowI) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.i(tag, content, tr)
    }

    fun v(content: String) {
        if (!allowV) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.v(tag, content)
    }

    fun v(content: String, tr: Throwable) {
        if (!allowV) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.v(tag, content, tr)
    }

    fun w(content: String) {
        if (!allowW) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.w(tag, content)
    }

    fun w(content: String, tr: Throwable) {
        if (!allowW) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.w(tag, content, tr)
    }

    fun w(tr: Throwable) {
        if (!allowW) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.w(tag, tr)
    }

    fun wtf(content: String) {
        if (!allowWtf) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.wtf(tag, content)
    }

    fun wtf(content: String, tr: Throwable) {
        if (!allowWtf) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.wtf(tag, content, tr)
    }

    fun wtf(tr: Throwable) {
        if (!allowWtf) return
        val caller = getCallerStackTraceElement()
        val tag = generateTag(caller)
        Log.wtf(tag, tr)
    }

    fun getCallerStackTraceElement(): StackTraceElement {
        return Thread.currentThread().stackTrace[4]
    }

}