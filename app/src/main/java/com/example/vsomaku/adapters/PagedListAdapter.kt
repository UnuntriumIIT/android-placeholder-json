package com.example.vsomaku.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.vsomaku.R
import com.example.vsomaku.controllers.PostInfoController
import com.example.vsomaku.data.Post
import kotlinx.android.synthetic.main.rcv_item_post.view.*

class PagedListAdapter(private val context : Context, private val router : Router, private var clickListener: ((Post)-> Unit)?) :
    PagedListAdapter<Post, com.example.vsomaku.adapters.PagedListAdapter.PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }

    inner class PostViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(post : Post) {
            itemView.tv_title.text = post.title
            itemView.tv_description.text = post.description

            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(PostInfoController.POST_KEY, post)

                val controller = PostInfoController(bundle)

                controller.args.putParcelable(PostInfoController.POST_KEY, post)
                router.pushController(RouterTransaction.with(controller))
            }
        }
    }
}