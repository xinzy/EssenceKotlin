package com.xinzy.essence.kotlin.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
class CategoryFragment : Fragment(), CategoryContract.View {

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
        mRecyclerView = find(R.id.categoryRecyclerView)

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
}