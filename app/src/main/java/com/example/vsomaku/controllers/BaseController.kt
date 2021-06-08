package com.example.vsomaku.controllers

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import moxy.MvpDelegate

abstract class BaseController : Controller {

    protected lateinit var context : Context
    private var mvpDelegate : MvpDelegate<BaseController>? = null
    private fun getMvpDelegate() : MvpDelegate<BaseController> {
        return if (mvpDelegate == null) {
            mvpDelegate = MvpDelegate(this)
            mvpDelegate!!
        } else {
            mvpDelegate!!
        }
    }

    constructor() : super() {
        inject()
        getMvpDelegate().onCreate()
    }

    constructor(bundle : Bundle) : super(bundle) {
        inject()
        getMvpDelegate().onCreate(bundle)
    }

    abstract fun inject()

    override fun onAttach(view: View) {
        super.onAttach(view)

        getMvpDelegate().onAttach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        getMvpDelegate().onSaveInstanceState(outState)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)

        getMvpDelegate().onDestroyView()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)

        getMvpDelegate().onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        getMvpDelegate().onDestroy()
    }
}