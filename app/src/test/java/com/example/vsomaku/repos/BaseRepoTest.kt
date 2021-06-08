package com.example.vsomaku.repos

import com.example.vsomaku.SomakuApi
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class BaseRepoTest {
    @Mock
    protected lateinit var errorConsumer: Consumer<Throwable>
    @Mock
    protected lateinit var api : SomakuApi

    @Before
    fun provideMainAndroidScheduler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
    }
}