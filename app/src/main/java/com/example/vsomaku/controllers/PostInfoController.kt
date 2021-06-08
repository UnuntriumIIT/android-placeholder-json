package com.example.vsomaku.controllers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.example.vsomaku.App
import com.example.vsomaku.R
import com.example.vsomaku.adapters.CommentariesAdapter
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.PostInfoPresenter
import com.example.vsomaku.presenters.views.PostInfoView
import kotlinx.android.synthetic.main.fragment_post_info.view.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class PostInfoController(bundle: Bundle) : BaseController(), PostInfoView {

    @Inject
    @InjectPresenter
    lateinit var presenter : PostInfoPresenter

    @ProvidePresenter
    fun providePresenter() : PostInfoPresenter = presenter

    override fun inject() {
        App.getComponent().inject(this)
    }

    init {
        val post = bundle.getParcelable<Post>(POST_KEY)
        presenter.attachPost(post!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        context = container.context

        return LayoutInflater.from(context).inflate(R.layout.fragment_post_info, container, false)
    }

    override fun bindComments(comments: List<Comment>) {
        view?.let {
            it.recycler_view.adapter = CommentariesAdapter(context, comments)
            it.recycler_view.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindUserInfo(user: User) {
        view?.let {
            it.tv_user_info.text = "(${user.userName}, ${user.name}, ${user.email})"
            it.cv_user.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(UserInfoController.USER_KEY, user)

                val controller = UserInfoController(bundle)
                router.pushController(RouterTransaction.with(controller))
            }
        }
    }

    override fun bindPostInfo(post: Post) {
        view?.let {
            it.tv_title.text = post.title
            it.tv_description.text = post.description
        }
    }

    override fun showLayout() {
        view?.let {
            it.progress_bar.visibility = View.GONE
            it.scroll_view.visibility = View.VISIBLE
        }
    }

    companion object {
        const val POST_KEY = "post_key"
    }
}