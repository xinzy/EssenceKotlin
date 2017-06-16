package com.xinzy.essence.kotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.ViewGroup
import com.xinzy.essence.kotlin.R
import com.xinzy.essence.kotlin.base.BaseActivity
import com.xinzy.essence.kotlin.base.find
import com.xinzy.essence.kotlin.fragment.CategoryFragment
import com.xinzy.essence.kotlin.util.CATEGORYS

class CategoryActivity : BaseActivity() {

    private lateinit var mViewPager: ViewPager

    private val mCategories = CATEGORYS
    private lateinit var mCategory: String

    companion object {
        val KEY_CATEGORY = "CATEGORY"
        fun start(context: Context, cat: String) {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra(KEY_CATEGORY, cat)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tabLayout: TabLayout = find(R.id.tabs)
        mViewPager = find(R.id.viewPager)
        val adapter = SectionsAdapter(supportFragmentManager)
        mViewPager.adapter = adapter
        tabLayout.setupWithViewPager(mViewPager)

        mCategory = intent.getStringExtra(KEY_CATEGORY) ?: ""
        for (index in mCategories.indices) {
            if (mCategories[index] == mCategory) {
                mViewPager.currentItem = index
                break
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    inner class SectionsAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int) = CategoryFragment.create(mCategories[position])

        override fun getCount(): Int = mCategories.size

        override fun getPageTitle(position: Int) = mCategories[position]

        override fun destroyItem(container: ViewGroup?, position: Int, obj: Any?) {}
    }
}
