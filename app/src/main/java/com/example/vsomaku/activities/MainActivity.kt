package com.example.vsomaku.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.RouterTransaction
import com.example.vsomaku.R
import com.example.vsomaku.controllers.PostsController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var router : com.bluelinelabs.conductor.Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!router.hasRootController())
            router.pushController(RouterTransaction.with(PostsController()))

        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        if (!router.handleBack())
            super.onBackPressed()
    }
}
