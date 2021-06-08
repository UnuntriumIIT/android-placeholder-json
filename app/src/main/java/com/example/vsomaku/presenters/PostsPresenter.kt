package com.example.vsomaku.presenters

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.paging.PagedList
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.MainThreadExecutor
import com.example.vsomaku.adapters.PostsDataSource
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import com.example.vsomaku.repos.PostRepo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.Executors

@InjectViewState
class PostsPresenter(private val postRepo: PostRepo, private val context : Context) : MvpPresenter<PostsView>() {

    private val disposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        getPagedList()
    }

    fun getPosts() {
        postRepo.loadPosts(Consumer { posts ->
            for (post : Post in posts) {
                post.title = post.title?.let { it[0].toUpperCase() + it.substring(1) }
                post.description = post.description?.let { it[0].toUpperCase() + it.replace("\n", "").substring(1) }
            }

            viewState.bindPosts(posts)
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        })
    }

    fun getPagedList() {
        val dataSource = PostsDataSource(postRepo, object : PostsDataSource.OnInitialDataLoaded {
            override fun dataLoaded() {
                viewState.showLayout()
            }
        })
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        disposable.add(Single.just(PagedList.Builder(dataSource, config)
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

        postRepo.destroy()
        disposable.dispose()
        disposable.clear()
    }
}