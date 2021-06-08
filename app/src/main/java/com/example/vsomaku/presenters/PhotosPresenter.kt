package com.example.vsomaku.presenters

import android.content.Context
import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Photo
import com.example.vsomaku.presenters.views.PhotoView
import com.example.vsomaku.repos.PhotoRepo
import io.reactivex.disposables.CompositeDisposable
import moxy.InjectViewState
import moxy.MvpPresenter
import io.reactivex.functions.Consumer

@InjectViewState
class PhotosPresenter (private val photoRepo: PhotoRepo, private val context : Context) : MvpPresenter<PhotoView>() {

    private val disposable = CompositeDisposable()
    private lateinit var photo : Photo

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val sp = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        getPhotos(sp.getInt("albumId", 0))
    }

    private fun getPhotos(albumId : Int) {
        photoRepo.loadPhotos(Consumer { photos ->
            Log.d(DEBUG_TAG, "LOAD PHOTOS")
            viewState.bindPhotos(photos)
            viewState.showLayout()
        }, Consumer {
            Log.d(DEBUG_TAG, "ERROR LOAD PHOTOS")
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, albumId)
    }

    override fun onDestroy() {
        super.onDestroy()

        photoRepo.destroy()
        disposable.dispose()
        disposable.clear()
    }
}