package com.xinzy.essence.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.util.Utils

/**
 * Created by Xinzy on 2017/6/16.
 */
class EssenceAdapter : RecyclerView.Adapter<EssenceAdapter.Holder>() {

    private val mDatas = mutableListOf<Essence>()
    private var mLayoutInflater: LayoutInflater? = null

    private var mOnClickListener: View.OnClickListener? = null

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.convert(mDatas[position])
    }

    override fun getItemCount() = mDatas.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent?.context)
        }
        return Holder(mLayoutInflater!!.inflate(R.layout.item_essence, parent, false))
    }

    fun setOnClickListener(l: View.OnClickListener) {
        mOnClickListener = l
    }

    fun replace(data: List<Essence>?) {
        mDatas.clear()
        if (data != null && data.isNotEmpty()) {
            mDatas.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addAll(data: List<Essence>?) {
        val count = mDatas.size
        if (data != null && data.isNotEmpty()) {
            mDatas.addAll(data)
        }
        notifyItemInserted(count)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.find(R.id.itemTitle)
        private val authorText: TextView = itemView.find(R.id.authorText)
        private val timeText: TextView = itemView.find(R.id.timeText)
        private val itemImg: ImageView = itemView.find(R.id.itemImg)

        init {
            itemView.setOnClickListener(mOnClickListener)
        }

        fun convert(essence: Essence) {
            titleText.text = essence.content
            authorText.text = if (TextUtils.isEmpty(essence.who)) "暂无" else essence.who
            timeText.text = Utils.format(essence.createdAt)
            if (essence.hasImg()) {
                itemImg.visibility = View.VISIBLE
                Picasso.with(itemImg.context).load(essence.getImg()).placeholder(R.drawable.img_default).into(itemImg)
            } else {
                itemImg.visibility = View.GONE
            }
        }
    }
}