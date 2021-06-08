package com.example.vsomaku.modules

import com.example.vsomaku.SomakuApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkUtilsModule {
    @Provides
    fun getSomakuApi() : SomakuApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SomakuApi::class.java)
    }

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}