package com.example.vsomaku.adapters

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.data.Post
import com.example.vsomaku.repos.PostRepo
import io.reactivex.functions.Consumer

class PostsDataSource(private val postRepo : PostRepo, private val onInitialDataLoaded : OnInitialDataLoaded) : PositionalDataSource<Post>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Post>) {
        postRepo.loadPosts(params.startPosition, params.startPosition + params.loadSize, Consumer { posts ->
            Log.d(DEBUG_TAG, "load posts from [${params.startPosition}] to [${params.startPosition + params.loadSize}]")
            changeData(posts)
            callback.onResult(posts)
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        })
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Post>) {
        postRepo.loadPosts(params.requestedStartPosition, params.requestedLoadSize,
            Consumer { posts ->
                Log.d(DEBUG_TAG, "load posts from [${params.requestedStartPosition}] to [${params.requestedLoadSize}]")
                changeData(posts)
                onInitialDataLoaded.dataLoaded()
                callback.onResult(posts, params.requestedStartPosition)
            }, Consumer {
                Log.d(DEBUG_TAG, it.localizedMessage)
            })
    }

    private fun changeData(posts : List<Post>) {
        for (post : Post in posts) {
            post.title = post.title?.let { it[0].toUpperCase() + it.substring(1) }
            post.description = post.description?.let { it[0].toUpperCase() + it.replace("\n", "").substring(1) }
        }
    }

    interface OnInitialDataLoaded {
        fun dataLoaded()
    }
}