package com.xinzy.essence.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by xinzy on 17/6/14.
 */
class BeautyAdapter : RecyclerView.Adapter<Holder> {

    private val mData: MutableList<Essence> = mutableListOf()

    private lateinit var mLayoutInflater: LayoutInflater

    constructor(list: List<Essence>?) {
        if (list != null) mData.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent!!.context)
        val view = mLayoutInflater.inflate(R.layout.item_beauty, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}

class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

}