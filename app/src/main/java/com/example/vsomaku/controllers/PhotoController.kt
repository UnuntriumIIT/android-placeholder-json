package com.example.vsomaku.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.adapters.PhotosAdapter
import com.example.vsomaku.data.Photo
import com.example.vsomaku.presenters.PhotosPresenter
import com.example.vsomaku.presenters.views.PhotoView
import kotlinx.android.synthetic.main.controller_photos.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PhotoController: BaseController(), PhotoView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PhotosPresenter

    @ProvidePresenter
    fun providePresenter() : PhotosPresenter = presenter

    override fun inject() {
        App.getComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        context = container.context
        return LayoutInflater.from(context).inflate(R.layout.controller_photos, container, false)
    }

    override fun bindPhotos(photos: List<Photo>) {
        view?.let {
            it.recycler_view_ph.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.recycler_view_ph.adapter = PhotosAdapter(context, photos)
        }
    }

    override fun showLayout() {
        view?.let {
            it.progress_bar_ph.visibility = View.GONE
            it.recycler_view_ph.visibility = View.VISIBLE
        }
    }
}