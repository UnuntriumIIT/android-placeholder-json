package com.example.vsomaku.presenters.views

import com.example.vsomaku.data.Photo

interface PhotoView : BaseView {
    fun bindPhotos(photos : List<Photo>)
}