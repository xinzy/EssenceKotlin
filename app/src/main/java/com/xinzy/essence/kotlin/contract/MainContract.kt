package com.xinzy.essence.kotlin.contract

import com.xinzy.essence.kotlin.base.BasePresenter
import com.xinzy.essence.kotlin.base.BaseView
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by xinzy on 17/6/14.
 */
interface MainContract {

    interface Presenter : BasePresenter {

        fun load(isRefresh: Boolean)

    }

    interface View : BaseView<Presenter> {
        fun showLoading()

        fun hideLoading()

        fun showData(data: List<Essence>?, refresh: Boolean)
    }
}