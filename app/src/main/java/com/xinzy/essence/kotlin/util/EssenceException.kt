package com.xinzy.essence.kotlin.util

/**
 * Created by shaozeng.yang on 2017/6/14.
 */
class EssenceException: Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)
}