package com.example.vsomaku.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vsomaku.daos.*
import com.example.vsomaku.data.*

@Database(entities = [Post::class, User::class, Comment::class, Album::class, Photo::class], version = 4, exportSchema = false)
abstract class SomakuDatabase : RoomDatabase() {
    abstract fun postDao() : PostDao
    abstract fun userDao() : UserDao
    abstract fun commentDao() : CommentDao
    abstract fun albumDao() : AlbumDao
    abstract fun photoDao() : PhotoDao
}