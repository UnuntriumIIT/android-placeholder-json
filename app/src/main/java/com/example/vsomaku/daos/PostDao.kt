package com.example.vsomaku.daos

import androidx.room.*
import com.example.vsomaku.data.Post
import io.reactivex.Single

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAll() : Single<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post : Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts : List<Post>)

    @Update
    fun updateAll(posts : List<Post>)
}