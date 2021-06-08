package com.example.vsomaku.repos

import com.example.vsomaku.SomakuApi
import io.reactivex.disposables.CompositeDisposable

abstract class BaseRepo(protected val api : SomakuApi) {

    protected val disposable = CompositeDisposable()

    fun destroy() {
        disposable.dispose()
    }

    override fun toString(): String {

        return this.javaClass.name
    }
}