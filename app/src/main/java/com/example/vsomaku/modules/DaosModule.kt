package com.example.vsomaku.modules

import com.example.vsomaku.daos.*
import com.example.vsomaku.databases.SomakuDatabase
import dagger.Module
import dagger.Provides

@Module
class DaosModule {
    @Provides
    fun providePostDao(database : SomakuDatabase) : PostDao {
        return database.postDao()
    }

    @Provides
    fun provideUserDao(database: SomakuDatabase) : UserDao {
        return database.userDao()
    }

    @Provides
    fun provideCommentDao(database : SomakuDatabase) : CommentDao {
        return database.commentDao()
    }

    @Provides
    fun provideAlbumDao(database: SomakuDatabase) : AlbumDao {
        return database.albumDao()
    }

    @Provides
    fun providePhotoDao(database: SomakuDatabase) : PhotoDao {
        return database.photoDao()
    }
}