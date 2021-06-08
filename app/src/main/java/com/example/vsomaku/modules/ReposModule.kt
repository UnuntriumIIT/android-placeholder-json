package com.example.vsomaku.modules

import com.example.vsomaku.SomakuApi
import com.example.vsomaku.daos.*
import com.example.vsomaku.repos.*
import dagger.Module
import dagger.Provides

@Module
class ReposModule {

    @Provides
    fun providePostRepo(dao : PostDao, api : SomakuApi) : PostRepo {
        return PostRepo(dao, api)
    }

    @Provides
    fun providePostInfoRepo(userDao: UserDao, commentDao: CommentDao, api : SomakuApi) : PostInfoRepo {
        return PostInfoRepo(userDao, commentDao, api)
    }

    @Provides
    fun provideUserInfoRepo(albumDao: AlbumDao, photoDao: PhotoDao, api : SomakuApi) : UserInfoRepo {
        return UserInfoRepo(albumDao, photoDao, api)
    }

    @Provides
    fun providePhotoRepo(dao: PhotoDao, api: SomakuApi) : PhotoRepo{
        return PhotoRepo(dao, api)
    }

    @Provides
    fun provideAlbumRepo(dao: AlbumDao, api: SomakuApi) : AlbumRepo {
        return AlbumRepo(dao, api)
    }
}