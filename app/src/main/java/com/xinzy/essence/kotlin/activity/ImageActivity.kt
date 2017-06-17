package com.xinzy.essence.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.model.Essence

class ImageActivity : BaseActivity() {

    private lateinit var appbarLayout: AppBarLayout

    private lateinit var mEssence: Essence
    private lateinit var photoView: PhotoView

    private var isAppbarVisible = true

    companion object {
        private val KEY_ESSENCE = "ESSENCE"
        fun start(activity: Activity, view: View, essence: Essence) {
            val intent = Intent(activity, ImageActivity::class.java)
            intent.putExtra("transition", "share")
            intent.putExtra(KEY_ESSENCE, essence)
            val sharedElementName = activity.getString(R.string.imageTransitionName)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, sharedElementName).toBundle()
            ActivityCompat.startActivity(activity, intent, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        appbarLayout = find(R.id.appbar)
        photoView = find(R.id.photoView)
        photoView.setOnPhotoTapListener { _, _, _ -> run {
            if (isAppbarVisible) {
                ViewCompat.animate(appbarLayout).alpha(0f).setDuration(500).start()
                isAppbarVisible = false
            } else {
                ViewCompat.animate(appbarLayout).alpha(1f).setDuration(500).start()
                isAppbarVisible = true
            }
        }}

        mEssence = intent.getParcelableExtra(KEY_ESSENCE)

        Picasso.with(this).load(mEssence.url).into(photoView)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            ActivityCompat.finishAfterTransition(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
