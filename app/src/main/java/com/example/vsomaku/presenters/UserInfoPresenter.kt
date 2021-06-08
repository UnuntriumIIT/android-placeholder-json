package com.example.vsomaku.presenters

import android.util.Log
import com.example.vsomaku.DEBUG_TAG
import com.example.vsomaku.daos.AlbumDao
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import com.example.vsomaku.repos.UserInfoRepo
import io.reactivex.functions.Consumer
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class UserInfoPresenter(private val repo : UserInfoRepo, private val albumDao : AlbumDao) : MvpPresenter<UserView>() {
    lateinit var user : User

    fun showUserInfo() {
        viewState.showUserInfo(user)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        showUserInfo()
        getAlbumsData()

        Log.d(DEBUG_TAG, "UserInfo first attached")
    }

    fun attachUser(user : User) {
        this.user = user
    }

    fun getAlbumsData() {
        repo.loadAlbumsData(Consumer {pair ->
            viewState.bindAlbumsInfo(pair.first, pair.second)
            viewState.showLayout()
        }, Consumer {
            Log.d(DEBUG_TAG, it.localizedMessage)
        }, user.id)
    }

    override fun onDestroy() {
        super.onDestroy()

        repo.destroy()
    }
}