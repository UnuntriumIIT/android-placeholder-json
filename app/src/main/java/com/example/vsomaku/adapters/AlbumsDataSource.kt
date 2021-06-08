package com.example.vsomaku.adapters

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Album
import com.example.vsomaku.repos.AlbumRepo
import io.reactivex.functions.Consumer

class AlbumsDataSource(private val userId : Int, private val albumRepo: AlbumRepo,
                       private val onInitialDataLoaded : AlbumsDataSource.OnInitialDataLoaded) : PositionalDataSource<Album>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Album>) {
        albumRepo.loadAlbums(Consumer { albums ->
            Log.d(DEBUG_TAG, "load albums")
            onInitialDataLoaded.dataLoaded()
            callback.onResult(albums, params.requestedStartPosition)
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, userId)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Album>) {}

    interface OnInitialDataLoaded {
        fun dataLoaded()
    }
}