package com.example.vsomaku.modules

import android.content.Context
import com.example.vsomaku.daos.AlbumDao
import com.example.vsomaku.presenters.*
import com.example.vsomaku.repos.*
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {

    @Provides
    fun providePostsPresenter(repo: PostRepo, context : Context) : PostsPresenter {
        return PostsPresenter(repo, context)
    }

    @Provides
    fun providePostInfoPresenter(repo : PostInfoRepo) : PostInfoPresenter {
        return PostInfoPresenter(repo)
    }

    @Provides
    fun provideUserInfoPresenter(repo : UserInfoRepo, albumDao: AlbumDao) : UserInfoPresenter {
        return UserInfoPresenter(repo, albumDao)
    }

    @Provides
    fun providePhotoPresenter(repo : PhotoRepo, context : Context) : PhotosPresenter {
        return PhotosPresenter(repo, context)
    }
}