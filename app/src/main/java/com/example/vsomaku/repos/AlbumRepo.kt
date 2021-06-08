package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.AlbumDao
import com.example.vsomaku.data.Album
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class AlbumRepo(private val albumDao : AlbumDao, api : SomakuApi) : BaseRepo(api) {
    fun loadAlbums(consumer: Consumer<List<Album>>, errorConsumer: Consumer<Throwable>, userId : Int) {
        disposable.add(api.getAlbums(userId)
            .map {response ->
                if (response.body() != null) response.body()!!
                else throw java.lang.Exception("albums null")
            }
            .toObservable()
            .doOnNext { albums : List<Album> ->
                albumDao.insertAll(albums)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, it.localizedMessage)
                Log.d(DEBUG_TAG,"Load albums from db in $this")
                albumDao.getAlbumsByUserId(userId)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                consumer.accept(it)
            }, {
                errorConsumer.accept(it)
            }))
    }
}