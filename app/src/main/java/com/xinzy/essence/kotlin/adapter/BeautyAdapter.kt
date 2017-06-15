package com.xinzy.essence.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by xinzy on 17/6/14.
 */
class BeautyAdapter : RecyclerView.Adapter<BeautyAdapter.Holder> {

    private val mData: MutableList<Essence> = mutableListOf()

    private var mLayoutInflater: LayoutInflater? = null
    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(list: List<Essence>?) {
        if (list != null) mData.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent!!.context)
        val view: View = mLayoutInflater!!.inflate(R.layout.item_beauty, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.convert(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(l: OnItemClickListener) {
        mOnItemClickListener = l
    }

    fun replace(list: List<Essence>?) {
        mData.clear()
        if (list != null) mData.addAll(list)
        notifyDataSetChanged()
    }

    fun addAll(list: List<Essence>?) {
        val count = mData.size
        if (list != null) mData.addAll(list)
        notifyItemInserted(count - 1)
    }

    inner class Holder : RecyclerView.ViewHolder, View.OnClickListener {
        private var img: ImageView
        private var text: TextView

        private lateinit var mItem: Essence

        constructor(itemView: View) : super(itemView) {
            img = itemView.find(R.id.itemImg)
            text = itemView.find(R.id.itemTitle)

            img.setOnClickListener(this)
            text.setOnClickListener(this)
        }

        fun convert(essence: Essence) {
            mItem = essence
            Picasso.with(img.context).load(essence.url).placeholder(R.drawable.img_default).into(img)
            text.text = essence.content
        }

        override fun onClick(v: View) {
            val parent = this@BeautyAdapter
            when (v.id) {
                R.id.itemImg -> parent.mOnItemClickListener?.onImageClick(mItem)
                R.id.itemTitle -> parent.mOnItemClickListener?.onTextClick(mItem)
            }

        }
    }

    interface OnItemClickListener {
        fun onImageClick(essence: Essence)

        fun onTextClick(essence: Essence)
    }
}
