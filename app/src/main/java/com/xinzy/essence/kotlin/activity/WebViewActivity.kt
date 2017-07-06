package com.xinzy.essence.kotlin.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.util.L

/**
 * Created by xinzy on 17/6/18.
 */

class WebViewActivity : BaseActivity(), NestedScrollView.OnScrollChangeListener, View.OnClickListener {

    private lateinit var mAppBar: AppBarLayout
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mScrollView: NestedScrollView
    private lateinit var mWebView: WebView
    private lateinit var mFab: FloatingActionButton

    private var mReloadMenuItem: MenuItem? = null

    private var isFabShown = true
    private var isFabAnim = false

    companion object {
        private val KEY_ESSENCE = "ESSENCE"

        fun start(context: Context, essence: Essence) {
            val intent: Intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(KEY_ESSENCE, essence)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAppBar = find(R.id.app_bar)
        mProgressBar = find(R.id.progressBar)
        mScrollView = find(R.id.scrollView)
        mWebView = find(R.id.webView)
        mFab = find(R.id.fab)

        mScrollView.setOnScrollChangeListener(this)
        mFab.setOnClickListener(this)

        mWebView.setWebViewClient(InnerWebViewClient())
        mWebView.setWebChromeClient(InnerWebChromeClient())
        val settings = mWebView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        val essence: Essence = intent.getParcelableExtra(KEY_ESSENCE)
        mWebView.loadUrl(essence.url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_webview, menu)
        mReloadMenuItem = menu?.findItem(R.id.menuRefresh);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab -> mScrollView.smoothScrollTo(0, 0)
        }
    }

    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        val delta = scrollY - oldScrollY

        if (delta > 0 && isFabShown && !isFabAnim) {
            isFabAnim = true
            isFabShown = false
            ViewCompat.animate(mFab).alpha(1f).setDuration(500).withLayer().withEndAction({isFabAnim = false}).start()
        } else if (delta < 0 && !isFabShown && !isFabAnim) {
            isFabAnim = true
            isFabShown = true
            ViewCompat.animate(mFab).alpha(0f).setDuration(500).withLayer().withEndAction({isFabAnim = false}).start()
        }
    }

    inner class InnerWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            mProgressBar.visibility = View.VISIBLE
            mReloadMenuItem?.isVisible = false
        }
    }

    inner class InnerWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            mProgressBar.progress = newProgress
            if (newProgress == 100) {
                mProgressBar.progress = View.GONE
                mReloadMenuItem?.isVisible = true
            }
        }
    }
}