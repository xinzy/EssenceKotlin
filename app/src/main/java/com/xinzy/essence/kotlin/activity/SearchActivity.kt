package com.xinzy.essence.kotlin.activity

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle

import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.BaseActivity
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import com.xinzy.essence.kotlin.adapter.EssenceAdapter
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.contract.SearchContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.presenter.SearchPresenter


class SearchActivity : BaseActivity(), SearchContract.View {

    private lateinit var mSearchView: SearchView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView

    private var mAdapter: EssenceAdapter = EssenceAdapter()

    private lateinit var mPresenter: SearchContract.Presenter

    private var mPage: Int = 1

    companion object {
        fun start(activity: Activity, view: View) {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra("transition", "share")
            val sharedElementName = activity.getString(R.string.shareTransition)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, sharedElementName).toBundle()
            ActivityCompat.startActivity(activity, intent, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mRefreshLayout = find(R.id.searchRefreshLayout)
        mRefreshLayout.isEnabled = false

        mRecyclerView = find(R.id.searchRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(RecyclerItemDecoration(this))
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener(object: EssenceAdapter.OnItemClickListener {
            override fun onItemClick(essence: Essence?) {
                essence?.let { WebViewActivity.start(this@SearchActivity, essence) }
            }
        })

        mSearchView = find(R.id.searchView)
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.requestFocusFromTouch()
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        mSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mPage = 1
                query(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mPage = 1
                query(newText)
                return true
            }
        })

        SearchPresenter(this)
    }

    private fun query(text: String?) {
        if (TextUtils.isEmpty(text)) {
            hideLoading()
            mPresenter.cancel()
            mAdapter.clear()
        } else {
            mPresenter.search(text!!, mPage)
        }
    }

    override fun setPresenter(presenter: SearchContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoading() {
        if (!mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if (mRefreshLayout.isRefreshing) {
            mRefreshLayout.isRefreshing = false
        }
    }

    override fun showData(data: List<Essence>?) {
        mAdapter.replace(data)
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
