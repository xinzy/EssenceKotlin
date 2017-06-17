package com.xinzy.essence.kotlin.presenter

import com.xinzy.essence.kotlin.api.ApiCallback
import com.xinzy.essence.kotlin.api.GankApi
import com.xinzy.essence.kotlin.api.GankApiRetrofitImpl
import com.xinzy.essence.kotlin.contract.CategoryContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.util.EssenceException
import com.xinzy.essence.kotlin.util.PER_PAGE

/**
 * Created by Xinzy on 2017/6/16.
 */

class CategoryPresenter : CategoryContract.Presenter {

    private val mView: CategoryContract.View
    private val mCategory: String

    private val mGankApi: GankApi

    private var isLoading = false
    private val mPageSize = PER_PAGE
    private var mPage = 1

    constructor(view: CategoryContract.View, cat: String) {
        mView = view
        mCategory = cat
        mGankApi = GankApiRetrofitImpl()

        mView.setPresenter(this)
    }


    override fun start() {
        loading(true)
    }

    override fun loading(refresh: Boolean) {
        if (isLoading) return
        isLoading = true

        if (refresh) mPage = 1
        mGankApi.category(mCategory, mPageSize, mPage, object: ApiCallback<List<Essence>> {

            override fun onStart() {
                if (refresh) mView.showRefresh()
            }

            override fun onSuccess(data: List<Essence>?) {
                isLoading = false
                mView.hideRefresh()
                mView.showData(data, refresh)
            }

            override fun onFailure(e: EssenceException) {
                isLoading = false
                mView.hideRefresh()
            }
        })
    }

}