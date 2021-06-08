package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.PostDao
import com.example.vsomaku.data.Post
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class PostRepo(private val postDao : PostDao, api : SomakuApi) : BaseRepo(api) {

    fun loadPosts(consumer: Consumer<List<Post>>, errorConsumer: Consumer<Throwable>) {
        disposable.add(api.getPosts()
            .map {response ->
                if (response.body() != null) response.body()!!
                else throw java.lang.Exception("posts null")
            }
            .toObservable()
            .doOnNext { posts : List<Post> ->
                postDao.insertAll(posts)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, it.localizedMessage)
                Log.d(DEBUG_TAG,"Load posts from db in $this")
                postDao.getAll()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                consumer.accept(it)
            }, {
                errorConsumer.accept(it)
            }))
    }

    fun loadPosts(startPos : Int, size : Int, consumer: Consumer<List<Post>>, errorConsumer: Consumer<Throwable>) {
        disposable.add(api.getPosts()
            .map {response ->
                if (response.body() != null) response.body()!!
                else throw java.lang.Exception("posts null")
            }
            .toObservable()
            .doOnNext { posts : List<Post> ->
                postDao.insertAll(posts)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, it.localizedMessage)
                Log.d(DEBUG_TAG,"Load posts from db in $this")
                postDao.getAll()
            }
            .toObservable()
            .flatMapIterable { it }
            .take(size.toLong())
            .skip(startPos.toLong())
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                consumer.accept(it)
            }, {
                errorConsumer.accept(it)
            }))
    }
}