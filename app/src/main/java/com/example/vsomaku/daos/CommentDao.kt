package com.example.vsomaku.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsomaku.data.Comment
import io.reactivex.Single

@Dao
interface CommentDao {
    @Query("SELECT * FROM Comment WHERE postId = :postId")
    fun getCommentsByPostId(postId : Int) : Single<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comments : List<Comment>)
}