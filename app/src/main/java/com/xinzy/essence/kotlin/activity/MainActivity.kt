package com.xinzy.essence.kotlin.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.contract.MainContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.presenter.MainPresenter

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, MainContract.View {

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout

    private lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = find(R.id.fab)
        fab.setOnClickListener({ view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        })

        mDrawerLayout = find(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = find(R.id.nav_view)
        navigationView.inflateMenu(R.menu.menu_main_nav)
        navigationView.setNavigationItemSelectedListener(this)

        val themeSwicher = MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuTheme)).findViewById(R.id.viewSwitch) as SwitchCompat
        themeSwicher.isChecked = isNightMode()
        themeSwicher.setOnCheckedChangeListener { buttonView, isChecked -> run {
            setNightMode(isChecked)
            recreate()
        }}

        mRecyclerView = find(R.id.recyclerView)
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView!!.canScrollVertically(1)) {
                    mPresenter.load(false)
                }
            }
        })
        mRefreshLayout = find(R.id.swipeRefreshLayout)
        mRefreshLayout.setOnRefreshListener(this)

        MainPresenter(this)
        mPresenter.start()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoading() {
        mRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        mRefreshLayout.isRefreshing = false
    }

    override fun showData(data: List<Essence>, refresh: Boolean) {

    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
