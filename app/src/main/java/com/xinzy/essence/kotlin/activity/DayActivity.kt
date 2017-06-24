package com.xinzy.essence.kotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import com.squareup.picasso.Picasso

import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.adapter.DayAdapter
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.contract.DayContract
import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.presenter.DayPresenter

class DayActivity : BaseActivity(), DayContract.View {

    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: DayAdapter

    private var mEssence: Essence? = null

    private lateinit var mPresenter: DayContract.Presenter

    companion object {
        private val KEY_ESSENCE = "ESSENCE"

        fun start(context: Context, essence: Essence) {
            val intent: Intent = Intent(context, DayActivity::class.java)
            intent.putExtra(KEY_ESSENCE, essence)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)

        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageView: ImageView = find(R.id.dayImage)

        mRefreshLayout = find(R.id.dayRefreshLayout)
        mRefreshLayout.setOnRefreshListener { mPresenter.refresh() }
        mRecyclerView = find(R.id.dayRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = DayAdapter()
        mAdapter.setOnItemClickListener(object: DayAdapter.OnItemClickListener{
            override fun onItemClick(essence: Essence?) {
                essence?.let { WebViewActivity.start(this@DayActivity, essence) }
            }
        })
        mRecyclerView.adapter = mAdapter

        mEssence = intent.getParcelableExtra(KEY_ESSENCE)

        Picasso.with(this).load(mEssence!!.url).into(imageView)
        DayPresenter(this, mEssence!!)
        mPresenter.start()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            ActivityCompat.finishAfterTransition(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: DayContract.Presenter) {
        mPresenter = presenter
    }

    override fun showRefresh() {
        mRefreshLayout.isRefreshing = true
    }

    override fun hideRefresh() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showTitle(title: String) {
        setTitle(title)
    }

    override fun showData(data: DayType?, refresh: Boolean) {
        if (refresh) {
            mAdapter.replace(data?.list())
        } else {
            mAdapter.addAll(data?.list())
        }
    }
}
