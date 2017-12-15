package com.xinzy.essence.kotlin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.AppBarLayout
import android.support.design.widget.FloatingActionButton
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
import com.xinzy.essence.kotlin.util.Utils
import java.io.File

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
        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fab: FloatingActionButton = find(R.id.fab)
        fab.setOnClickListener { _ ->
            val pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val filename = "" + System.currentTimeMillis() + ".jpg"
            Utils.download(mEssence.url, File(pictureDir, filename))
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
