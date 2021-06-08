package com.example.vsomaku.presenters.views

import androidx.paging.PagedList
import com.example.vsomaku.data.Post

interface PostsView : BaseView {
    fun bindPosts(posts : List<Post>)
    fun bindPagedList(pagedList: PagedList<Post>)
}