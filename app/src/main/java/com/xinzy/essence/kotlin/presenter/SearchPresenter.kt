package com.xinzy.essence.kotlin.presenter

import com.xinzy.essence.kotlin.api.ApiCallback
import com.xinzy.essence.kotlin.api.GankApi
import com.xinzy.essence.kotlin.api.GankApiRetrofitImpl
import com.xinzy.essence.kotlin.contract.SearchContract
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.model.ListSimple
import com.xinzy.essence.kotlin.util.EssenceException
import com.xinzy.essence.kotlin.util.L
import com.xinzy.essence.kotlin.util.PER_PAGE
import retrofit2.Call

/**
 * Created by Xinzy on 2017/7/10.
 */
class SearchPresenter(view: SearchContract.View) : SearchContract.Presenter {

    private val mView: SearchContract.View = view
    private val gankApi: GankApi

    private var lastCall: Call<ListSimple<Essence>>? = null
    private var category: String = "Android"

    init {
        mView.setPresenter(this)
        gankApi = GankApiRetrofitImpl()
    }

    override fun start() {
    }

    override fun category(category: String) {
        this.category = category
    }

    override fun search(text: String, page: Int) {
        cancel()

        lastCall = gankApi.search(text, category, PER_PAGE, page, object: ApiCallback<List<Essence>> {
            override fun onStart() {
                mView.showLoading()
            }

            override fun onSuccess(data: List<Essence>?) {
                mView.hideLoading()
                mView.showData(data)
            }

            override fun onFailure(e: EssenceException) {
                if (e.toString().contains("Canceled")) {
                    mView.hideLoading()
                    mView.showData(null)
                }
            }
        })
    }

    override fun cancel() {
        if (lastCall != null && !lastCall!!.isCanceled) {
            lastCall!!.cancel()
        }
    }
}