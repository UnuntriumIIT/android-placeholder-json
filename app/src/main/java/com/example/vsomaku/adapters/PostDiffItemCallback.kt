package com.example.vsomaku.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.vsomaku.data.Post

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return  oldItem.title == newItem.title && oldItem.description == newItem.description
    }

}