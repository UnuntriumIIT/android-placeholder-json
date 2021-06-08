package com.example.vsomaku.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsomaku.data.Photo
import io.reactivex.Single

@Dao
interface PhotoDao {
    @Query("SELECT * FROM Photo WHERE albumId = :albumId")
    fun getPhotosByAlbumId(albumId : Int) : Single<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos : List<Photo>)
}