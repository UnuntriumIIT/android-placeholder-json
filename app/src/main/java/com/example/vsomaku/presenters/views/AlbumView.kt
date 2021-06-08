package com.example.vsomaku.presenters.views

import androidx.paging.PagedList
import com.example.vsomaku.data.Album

interface AlbumView : BaseView {
    fun bindAlbums(albums : List<Album>)
    fun bindPagedList(pagedList: PagedList<Album>)
}