package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.PhotoDao
import com.example.vsomaku.data.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class PhotoRepo(val photoDao : PhotoDao, api : SomakuApi) : BaseRepo(api) {

    fun loadPhotos(consumer: Consumer<List<Photo>>, errorConsumer: Consumer<Throwable>, albumId : Int) {
        disposable.add(api.getPhotos(albumId)
            .map {response ->
                if (response.body() != null) response.body()!!
                else throw java.lang.Exception("photos null")
            }
            .toObservable()
            .doOnNext { photos : List<Photo> ->
                photoDao.insertAll(photos)
            }
            .firstOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, it.localizedMessage)
                Log.d(DEBUG_TAG,"Load photos from db in $this")
                photoDao.getPhotosByAlbumId(albumId)
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