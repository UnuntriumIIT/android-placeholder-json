package com.example.vsomaku.modules

import android.content.Context
import androidx.room.Room
import com.example.vsomaku.databases.SomakuDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {
    @Provides
    fun provideDatabase(context : Context) : SomakuDatabase {
        return Room.databaseBuilder(context, SomakuDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }
}