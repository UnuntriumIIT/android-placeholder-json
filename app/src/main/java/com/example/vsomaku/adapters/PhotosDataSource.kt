package com.example.vsomaku.adapters

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Photo
import com.example.vsomaku.repos.PhotoRepo
import io.reactivex.functions.Consumer

class PhotosDataSource (val albumId : Int, private val photoRepo: PhotoRepo,
                        private val onInitialDataLoaded : PhotosDataSource.OnInitialDataLoaded) : PositionalDataSource<Photo>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Photo>) {
        photoRepo.loadPhotos(Consumer { photos ->
                Log.d(DEBUG_TAG, "load photos")
                onInitialDataLoaded.dataLoaded()
                callback.onResult(photos, params.requestedStartPosition)
            }, Consumer {
                Log.d(DEBUG_TAG, it.localizedMessage)
            }, albumId)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Photo>) {}

    interface OnInitialDataLoaded {
        fun dataLoaded()
    }
}