package com.example.vsomaku.presenters

import com.example.vsomaku.presenters.views.BaseView

abstract class BasePresenter<V : BaseView>  {
    protected var view : V? = null

    fun bindView(view : V) {
        this.view = view
    }

    fun unbindView() {
        this.view = null
    }

    abstract fun onDestroy()
}