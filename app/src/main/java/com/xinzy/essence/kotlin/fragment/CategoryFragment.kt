package com.xinzy.essence.kotlin.fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.adapter.EssenceAdapter
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.contract.CategoryContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.presenter.CategoryPresenter

/**
 * Created by Xinzy on 2017/6/16.
 *
 */
class CategoryFragment : Fragment(), CategoryContract.View, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mCategory: String

    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private val mAdapter = EssenceAdapter()

    private lateinit var mPresenter: CategoryContract.Presenter

    private var isInit = false
    private var isLoaded = false
    private var isShown = false

    companion object {
        val KEY_CATEGORY = "CATEGORY"

        fun create(cat: String): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putString(KEY_CATEGORY, cat)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCategory = arguments.getString(KEY_CATEGORY) ?: ""
        CategoryPresenter(this, mCategory)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRefreshLayout = find(R.id.categoryRefreshLayout)
        mRefreshLayout.setOnRefreshListener(this)
        mRecyclerView = find(R.id.categoryRecyclerView)

        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView!!.canScrollVertically(1)) {
                    mPresenter.loading(false)
                }
            }
        })
        mRecyclerView.addItemDecoration(RecyclerItemDecoration(context))

        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.setOnClickListener(View.OnClickListener { /* TODO */ })
        mRecyclerView.adapter = mAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isInit = true
        loading()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isShown = true
            loading()
        }
    }

    private fun loading() {
        if (isInit && isShown && !isLoaded) {
            isLoaded = true
            mPresenter.start()
        }
    }

    override fun onRefresh() {
        mPresenter.loading(true)
    }

    override fun setPresenter(presenter: CategoryContract.Presenter) {
        mPresenter = presenter
    }

    override fun showRefresh() {
        mRefreshLayout.isRefreshing = true
    }

    override fun hideRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showData(data: List<Essence>?, refresh: Boolean) {
        if (refresh) {
            mAdapter.replace(data)
        } else {
            mAdapter.addAll(data)
        }
    }

    inner class RecyclerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val mDividerHeight: Int

        init {
            mPaint.color = ContextCompat.getColor(context, R.color.colorDivide)
            mDividerHeight = (context.resources.displayMetrics.density * 4).toInt()
        }

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.set(0, 0, 0, mDividerHeight)
        }

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
            val left = parent.paddingLeft
            val right = parent.measuredWidth - parent.paddingRight
            val count = parent.childCount

            (0 until count).map { parent.getChildAt(it) }.forEach {
                val param = it.layoutParams as RecyclerView.LayoutParams
                val top = it.bottom + param.bottomMargin
                val bottom = top + mDividerHeight

                c.drawRect(Rect(left, top, right, bottom), mPaint)
            }
        }
    }
}