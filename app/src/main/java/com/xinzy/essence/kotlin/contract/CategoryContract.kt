package com.xinzy.essence.kotlin.contract

import com.xinzy.essence.kotlin.base.BasePresenter
import com.xinzy.essence.kotlin.base.BaseView
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by Xinzy on 2017/6/16.
 *
 */
interface CategoryContract {

    interface Presenter : BasePresenter {
        fun loading(refresh: Boolean)
    }

    interface View : BaseView<Presenter> {
        fun showRefresh()

        fun hideRefresh()

        fun showData(data: List<Essence>?, refresh: Boolean)
    }

}