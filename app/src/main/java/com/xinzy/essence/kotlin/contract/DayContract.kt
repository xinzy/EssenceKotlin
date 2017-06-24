package com.xinzy.essence.kotlin.contract

import com.xinzy.essence.kotlin.base.BasePresenter
import com.xinzy.essence.kotlin.base.BaseView
import com.xinzy.essence.kotlin.model.DayType

/**
 * Created by xinzy on 17/6/24.
 */
interface DayContract {

    interface Presenter : BasePresenter {
        fun refresh()
    }

    interface View : BaseView<Presenter> {

        fun showRefresh()

        fun hideRefresh()

        fun showTitle(title: String)

        fun showData(data: DayType?, refresh: Boolean)
    }

}