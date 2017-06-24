package com.xinzy.essence.kotlin.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
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

/**
 * Created by xinzy on 17/6/18.
 */

class WebViewActivity : BaseActivity() {

    private lateinit var mAppBar: AppBarLayout
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mScrollView: NestedScrollView
    private lateinit var mWebView: WebView
    private lateinit var mFab: FloatingActionButton


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

        mWebView.setWebViewClient(InnerWebViewClient())
        mWebView.setWebChromeClient(InnerWebChromeClient())
        val settings = mWebView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        val essence: Essence = intent.getParcelableExtra(KEY_ESSENCE)
        mWebView.loadUrl(essence.url)
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

    inner class InnerWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            mProgressBar.visibility = View.VISIBLE
        }
    }

    inner class InnerWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            mProgressBar.progress = newProgress
            if (newProgress == 100) {
                mProgressBar.progress = View.GONE
            }
        }
    }
}