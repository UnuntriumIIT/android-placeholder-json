package com.example.vsomaku.presenters

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.paging.PagedList
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.MainThreadExecutor
import com.example.vsomaku.adapters.AlbumsDataSource
import com.example.vsomaku.presenters.views.AlbumView
import com.example.vsomaku.repos.AlbumRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.Executors

@InjectViewState
class AlbumsPresenter(private val userId: Int, private val albumRepo: AlbumRepo, private val context : Context) : MvpPresenter<AlbumView>() {
    private val disposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getPagedList()
    }

    fun getPagedList() {
        val dataSource = AlbumsDataSource(userId, albumRepo, object : AlbumsDataSource.OnInitialDataLoaded {
            override fun dataLoaded() {
                viewState.showLayout()
            }
        })
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        disposable.add(
            Single.just(
                PagedList.Builder(dataSource, config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .setNotifyExecutor(MainThreadExecutor(Handler(context.mainLooper)))
                    .build()
            )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pagedList ->
                    viewState.bindPagedList(pagedList)
                }, {
                    Log.d(DEBUG_TAG, it.localizedMessage)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()

        albumRepo.destroy()
        disposable.dispose()
        disposable.clear()
    }
}