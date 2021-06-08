package com.example.vsomaku.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsomaku.data.Album
import io.reactivex.Single

@Dao
interface AlbumDao {
    @Query("SELECT * FROM Album WHERE userId = :userId")
    fun getAlbumsByUserId(userId : Int) : Single<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(albums : List<Album>)
}