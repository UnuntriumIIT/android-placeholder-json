package com.example.vsomaku.repos

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.AlbumDao
import com.example.vsomaku.daos.PhotoDao
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class UserInfoRepo(private val albumDao : AlbumDao, private val photoDao: PhotoDao, api : SomakuApi) : BaseRepo(api) {
    fun loadAlbumsData(consumer : Consumer<Pair<List<Album>, List<Photo>>>, errorConsumer : Consumer<Throwable>, userId : Int) {
        disposable.add(api.getAlbums(userId)
                .map {
                    if (it.body() != null) it.body()!!
                    else throw Exception("null albums")
                }
                .toObservable()
                .doOnNext { albums: List<Album> ->
                    albumDao.insertAll(albums)
                }
                .firstOrError()
                .onErrorResumeNext {
                    Log.d(DEBUG_TAG, "albums loaded from db in $this")
                    errorConsumer.accept(it)
                    albumDao.getAlbumsByUserId(userId)
                }
                .toObservable()
                .flatMap {
                    Observable.zip(
                        Observable.just(it),
                        createPhotosObserver(it).toObservable(),
                        BiFunction<List<Album>, List<Photo>, Pair<List<Album>, List<Photo>>> { albums, photos ->
                            albums to photos
                        }
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    consumer.accept(it)
                }, {
                    errorConsumer.accept(it)
                }))
    }

    private fun createPhotosObserver(albums : List<Album>) : Single<List<Photo>> {
        val requests = arrayListOf<Single<Response<List<Photo>>>>()
        val dbRequests = arrayListOf<Single<List<Photo>>>()
        for (album : Album in albums) {
            requests.add(api.getPhotos(album.id))
            dbRequests.add(photoDao.getPhotosByAlbumId(album.id))
        }


        return Single.merge(requests)
            .map {
                if (it.body() != null) it.body()!!
                else throw Exception("null body")
            }
            .flatMapIterable {
                it
            }
            .toList()
            .toObservable()
            .doOnNext {
                photoDao.insertAll(it)
            }
            .singleOrError()
            .onErrorResumeNext {
                Log.d(DEBUG_TAG, "Load photos from db")
                Single.merge(dbRequests)
                    .flatMapIterable {
                        it
                    }
                    .toList()
            }
    }
}