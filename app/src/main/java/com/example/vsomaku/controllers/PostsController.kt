package com.example.vsomaku.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.adapters.PagedListAdapter
import com.example.vsomaku.adapters.PostsAdapter
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.PostsPresenter
import com.example.vsomaku.presenters.views.PostsView
import kotlinx.android.synthetic.main.controller_posts.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PostsController : BaseController(), PostsView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PostsPresenter

    @ProvidePresenter
    fun providePresenter() : PostsPresenter = presenter

    override fun inject() {
        App.getComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        context = container.context

        return LayoutInflater.from(context).inflate(R.layout.controller_posts, container, false)
    }

    override fun bindPosts(posts: List<Post>) {
        view?.let {
            it.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.recycler_view.adapter = PostsAdapter(context, posts, null)
        }
    }

    override fun bindPagedList(pagedList: PagedList<Post>) {
        val pagedListAdapter = PagedListAdapter(context, router){

        }
        pagedListAdapter.submitList(pagedList)

        view?.let {
            it.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            it.recycler_view?.adapter = pagedListAdapter
        }
    }

    override fun showLayout() {
        view?.let {
            it.progress_bar.visibility = View.GONE
            it.recycler_view.visibility = View.VISIBLE
        }
    }
}