package com.xinzy.essence.kotlin.presenter

import com.xinzy.essence.kotlin.api.ApiCallback
import com.xinzy.essence.kotlin.api.GankApi
import com.xinzy.essence.kotlin.api.GankApiRetrofitImpl
import com.xinzy.essence.kotlin.contract.DayContract
import com.xinzy.essence.kotlin.model.DayType
import com.xinzy.essence.kotlin.model.Essence
import com.xinzy.essence.kotlin.util.EssenceException
import java.util.*

/**
 * Created by xinzy on 17/6/24.
 */
class DayPresenter(view: DayContract.View, essence: Essence) : DayContract.Presenter {

    private var mView: DayContract.View = view
    private var mEssence: Essence = essence

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var mGankApi: GankApi
    private var isLoading = false

    init {
        val calendar = Calendar.getInstance()
        calendar.time = mEssence.createdAt

        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)

        mGankApi = GankApiRetrofitImpl()

        mView.setPresenter(this)
        mView.showTitle("干货 $year-$month-$day")
    }

    override fun start() {
        loading(false)
    }

    override fun refresh() {
        loading(true)
    }

    private fun loading(refresh: Boolean) {
        if (isLoading) return
        isLoading = true

        mGankApi.day(year, month, day, object: ApiCallback<DayType> {
            override fun onStart() {
                mView.showRefresh()
            }

            override fun onSuccess(data: DayType?) {
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