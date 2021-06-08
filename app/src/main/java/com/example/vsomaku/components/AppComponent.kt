package com.example.vsomaku.components

import android.content.Context
import com.example.vsomaku.SomakuApi
import com.example.vsomaku.controllers.PhotoController
import com.example.vsomaku.controllers.PostInfoController
import com.example.vsomaku.controllers.PostsController
import com.example.vsomaku.controllers.UserInfoController
import com.example.vsomaku.daos.*
import com.example.vsomaku.databases.SomakuDatabase
import com.example.vsomaku.modules.*
import com.example.vsomaku.repos.PostRepo
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppContextModule::class, PresentersModule::class
    , ReposModule::class, DatabaseModule::class, NetworkUtilsModule::class, DaosModule::class])
interface AppComponent {
    fun inject(controller : PostsController)
    fun inject(controller : PostInfoController)
    fun inject(controller : UserInfoController)
    fun inject(controller: PhotoController)
    fun getContext() : Context
    fun getPostsRepo() : PostRepo
    @Singleton fun getApi() : SomakuApi
    @Singleton fun getDatabase() : SomakuDatabase
    fun getPostDao() : PostDao
    fun getUserDao() : UserDao
    fun getCommentDao() : CommentDao
    fun getAlbumDao() : AlbumDao
    fun getPhotoDao() : PhotoDao
}