package com.example.vsomaku

import com.example.vsomaku.data.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SomakuApi {
    @GET("posts")
    fun getPosts() : Single<Response<List<Post>>>

    @GET("comments")
    fun getComments(@Query("postId") postId : Int) : Single<Response<List<Comment>>>

    @GET("users")
    fun getUser(@Query("id") userId : Int) : Single<Response<List<User>>>

    @GET("albums")
    fun getAlbums(@Query("userId") userId : Int) : Single<Response<List<Album>>>

    @GET("photos")
    fun getPhotos(@Query("albumId") albumId : Int) : Single<Response<List<Photo>>>
}