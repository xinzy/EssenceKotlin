package com.xinzy.essence.kotlin.contract

import com.xinzy.essence.kotlin.base.BasePresenter
import com.xinzy.essence.kotlin.base.BaseView
import com.xinzy.essence.kotlin.model.Essence

/**
 * Created by Xinzy on 2017/7/10.
 */
interface SearchContract {

    interface Presenter: BasePresenter {
        fun search(text: String, page: Int)

        fun category(category: String)

        fun cancel()
    }

    interface View: BaseView<Presenter> {

        fun showLoading()

        fun hideLoading()

        fun showData(data: List<Essence>?)
    }
}