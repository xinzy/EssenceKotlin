package com.xinzy.essence.kotlin.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.adapter.BeautyAdapter
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.base.snack
import com.xinzy.essence.kotlin.contract.MainContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.presenter.MainPresenter

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, MainContract.View {
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 100;

    private lateinit var mDrawerLayout: DrawerLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout
    private lateinit var mAdapter: BeautyAdapter

    private lateinit var mPresenter: MainContract.Presenter

    private var mLastPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = find(R.id.fab)
        fab.setOnClickListener{ _ -> mPresenter.start()}

        mDrawerLayout = find(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = find(R.id.nav_view)
        navigationView.inflateMenu(R.menu.menu_main_nav)
        navigationView.setNavigationItemSelectedListener(this)

        val themeSwitcher: SwitchCompat = MenuItemCompat.getActionView(navigationView.menu.findItem(R.id.menuTheme)).find(R.id.viewSwitch)
        themeSwitcher.isChecked = isNightMode()
        themeSwitcher.setOnCheckedChangeListener { _, isChecked -> run {
            setNightMode(isChecked)
            recreate()
        }}

        mRecyclerView = find(R.id.recyclerView)
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView!!.canScrollVertically(1)) {
                    mPresenter.loading(false)
                }
            }
        })
        mAdapter = BeautyAdapter(null)
        mAdapter.setOnItemClickListener(object: BeautyAdapter.OnItemClickListener {
            override fun onImageClick(view: View, essence: Essence) {
                ImageActivity.start(this@MainActivity, view, essence)
            }

            override fun onTextClick(view: View, essence: Essence) {
                DayActivity.start(this@MainActivity, essence)
            }
        })
        mRecyclerView.adapter = mAdapter

        mRefreshLayout = find(R.id.swipeRefreshLayout)
        mRefreshLayout.setOnRefreshListener(this)

        MainPresenter(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mPresenter.start()
        } else {
            requestPermission()
        }
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

    override fun showData(data: List<Essence>?, refresh: Boolean) {
        if (refresh) mAdapter.replace(data)
        else mAdapter.addAll(data)
    }

    override fun onRefresh() {
        mPresenter.loading(true)
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val time = SystemClock.uptimeMillis()
            if (time - mLastPressTime < 2000) {
                super.onBackPressed()
            } else {
                mLastPressTime = time
                snack(mRecyclerView, getString(R.string.pressToExit))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> run {
                AboutActivity.start(this)
                return true
            }
            R.id.action_search -> {
                SearchActivity.start(this, find(R.id.mainAppBar))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.menuCategory) {
            CategoryActivity.start(this, item.title.toString())
        } else {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.start()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder(this).setMessage(R.string.remindWriteExternalStorage)
                            .setPositiveButton(R.string.ok, {_, _ -> requestPermission() })
                            .setNegativeButton(R.string.cancel, null).show()
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
    }
}
