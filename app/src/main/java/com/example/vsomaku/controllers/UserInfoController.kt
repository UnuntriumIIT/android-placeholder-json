package com.example.vsomaku.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.adapters.AlbumsAdapter
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.UserInfoPresenter
import com.example.vsomaku.presenters.views.UserView
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class UserInfoController(bundle : Bundle) : BaseController(bundle), UserView {

    @Inject
    @InjectPresenter
    lateinit var presenter : UserInfoPresenter

    @ProvidePresenter
    fun providePresenter() : UserInfoPresenter = presenter

    override fun inject() {
        App.getComponent().inject(this)
    }

    init {
        val user = bundle.getParcelable<User>(USER_KEY)
        presenter.attachUser(user!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        context = container.context
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_user_info, container, false)
        view.albums_list_id99.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun showUserInfo(user: User) {
        view?.let {
            it.tv_name.text = user.name
            it.tv_user_name.text = user.userName
            it.tv_email.text = user.email
        }
    }

    override fun bindAlbumsInfo(albums: List<Album>, photos: List<Photo>) {
        view?.let {
            it.tv_albums.text = albums.size.toString()
            it.tv_photos.text = photos.size.toString()
            it.albums_list_id99.adapter = AlbumsAdapter(context, albums, router)
        }
    }

    override fun showLayout() {
        view?.let {
            it.progress_bar.visibility = View.GONE
            it.scroll_view.visibility = View.VISIBLE
        }
    }

    companion object {
        const val USER_KEY = "user_key"
    }
}