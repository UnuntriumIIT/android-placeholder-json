package com.example.vsomaku.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vsomaku.data.User
import io.reactivex.Single

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :id")
    fun getUserById(id : Int) : Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : User)
}