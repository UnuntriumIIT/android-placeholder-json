package com.example.vsomaku

import android.os.Handler
import java.util.concurrent.Executor

class MainThreadExecutor(private val handler : Handler) : Executor {
    override fun execute(p0: Runnable?) {
        handler.post(p0)
    }
}