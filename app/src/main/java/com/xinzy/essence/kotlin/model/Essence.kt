package com.xinzy.essence.kotlin.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Xinzy on 2017/6/14.
 *
 */
@Keep
class Essence : Parcelable {
    constructor()

    private constructor(source: Parcel) {
        id = source.readString()
        createdAt = Date(source.readLong())
        publishedAt = source.readString()
        content = source.readString()
        this.source = source.readString()
        type = source.readString()
        url = source.readString()
        used = source.readInt() == 1
        who = source.readString()
        source.readStringList(images)
        readability = source.readString()
    }

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
    var images: MutableList<String> = mutableListOf()
    var readability: String = ""

    fun hasImg() = images.isNotEmpty()
    fun getImg() = if (hasImg()) images[0] else ""

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Essence> = object : Parcelable.Creator<Essence> {
            override fun createFromParcel(source: Parcel): Essence = Essence(source)
            override fun newArray(size: Int): Array<Essence?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeLong(createdAt.time)
        dest.writeString(publishedAt)
        dest.writeString(content)
        dest.writeString(source)
        dest.writeString(type)
        dest.writeString(url)
        dest.writeInt(if (used) 1 else 0)
        dest.writeString(who)
        dest.writeStringList(images)
        dest.writeString(readability)
    }

}
