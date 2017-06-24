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
class DayAdapter : RecyclerView.Adapter<DayAdapter.Holder>() {
    private val TYPE_CATEGORY = 1
    private val TYPE_ESSENCE = 2

    private val mDatas = mutableListOf<Any>()
    private var mLayoutInflater: LayoutInflater? = null

    private var onItemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.convert(mDatas[position])
    }

    override fun getItemCount() = mDatas.size

    override fun getItemViewType(position: Int): Int {
        val item = mDatas[position]
        return if (item is String) TYPE_CATEGORY else TYPE_ESSENCE
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent?.context)
        }

        when (viewType) {
            TYPE_CATEGORY -> return CategoryHolder(mLayoutInflater!!.inflate(R.layout.item_category, parent, false))
//            TYPE_ESSENCE
            else ->  {
                return EssenceHolder(mLayoutInflater!!.inflate(R.layout.item_essence, parent, false))
            }
        }
    }

    fun setOnItemClickListener(l: OnItemClickListener) {
        onItemClickListener = l
    }

    fun replace(data: List<Any>?) {
        mDatas.clear()
        if (data != null && data.isNotEmpty()) {
            mDatas.addAll(data)
        }
        notifyDataSetChanged()
    }

    fun addAll(data: List<Any>?) {
        val count = mDatas.size
        if (data != null && data.isNotEmpty()) {
            mDatas.addAll(data)
        }
        notifyItemInserted(count)
    }

    inner abstract class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun convert(data: Any)
    }

    inner class EssenceHolder(itemView: View) : Holder(itemView) {
        private val titleText: TextView = itemView.find(R.id.itemTitle)
        private val authorText: TextView = itemView.find(R.id.authorText)
        private val timeText: TextView = itemView.find(R.id.timeText)
        private val itemImg: ImageView = itemView.find(R.id.itemImg)

        private var essence: Essence? = null

        init {
            itemView.setOnClickListener { onItemClickListener?.onItemClick(essence) }
        }

        override fun convert(data: Any) {

            this.essence = data as Essence?

            essence?.let {
                titleText.text = essence!!.content
                authorText.text = if (TextUtils.isEmpty(essence!!.who)) "暂无" else essence!!.who
                timeText.text = Utils.format(essence!!.createdAt)
                if (essence != null && essence!!.hasImg()) {
                    itemImg.visibility = View.VISIBLE
                    Picasso.with(itemImg.context).load(essence!!.getImg()).placeholder(R.drawable.img_default).into(itemImg)
                } else {
                    itemImg.visibility = View.GONE
                }
            }
        }
    }

    inner class CategoryHolder(itemView: View) : Holder(itemView) {

        private val textView: TextView = itemView.find(R.id.itemTitle)

        override fun convert(data: Any) {
            val title = data as String
            textView.text = title
        }
    }

    interface OnItemClickListener {
        fun onItemClick(essence: Essence?)
    }
}