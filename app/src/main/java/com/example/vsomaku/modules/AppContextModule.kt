package com.example.vsomaku.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppContextModule(private val context : Context) {
    @Provides
    fun getContext() : Context = context
}