package com.xinzy.essence.kotlin.presenter

import com.xinzy.essence.kotlin.api.ApiCallback
import com.xinzy.essence.kotlin.api.GankApi
import com.xinzy.essence.kotlin.api.GankApiRetrofitImpl
import com.xinzy.essence.kotlin.contract.MainContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.util.EssenceException
import com.xinzy.essence.kotlin.util.PER_PAGE

/**
 * Created by xinzy on 17/6/14.
 */
class MainPresenter : MainContract.Presenter {

    private var mView: MainContract.View
    private var mGankApi: GankApi

    private var mCategory = "福利"

    private var mPage = 1
    private var mPerPage = PER_PAGE
    private var isLoading = false

    constructor(view: MainContract.View) {
        mView = view
        mView.setPresenter(this)

        mGankApi = GankApiRetrofitImpl()
    }

    override fun loading(refresh: Boolean) {
        if (isLoading) return
        isLoading = true

        if (refresh) mPage = 1

        mGankApi.category(mCategory, mPerPage, mPage, object: ApiCallback<List<Essence>> {
            override fun onStart() {
                if (refresh) mView.showLoading()
            }

            override fun onSuccess(data: List<Essence>?) {
                isLoading = false
                mView.hideLoading()
                mView.showData(data, refresh)
                mPage ++
            }

            override fun onFailure(e: EssenceException) {
                isLoading = false
                mView.hideLoading()
            }
        })
    }

    override fun start() {
        loading(true)
    }
}