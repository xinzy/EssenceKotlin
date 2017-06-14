package com.xinzy.essence.kotlin.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Xinzy on 2017/6/14.
 */

class Essence {

    @SerializedName(value = "_id", alternate = arrayOf("ganhuo_id"))
    var id: String = ""
    var createdAt: Date = Date()
    var publishedAt: String = ""
    @SerializedName("desc")
    var content: String = ""
    var source: String = ""
    var type: String = ""
    var url: String = ""
    var used: Boolean = false
    var who: String = ""
    var images: Array<String> = arrayOf()
    var readability: String = ""
}
