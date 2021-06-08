package com.example.vsomaku.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vsomaku.R
import com.example.vsomaku.data.Comment
import kotlinx.android.synthetic.main.rcv_item_comment.view.*

class CommentariesAdapter(private val context : Context, private val comments : List<Comment>)
    : RecyclerView.Adapter<CommentariesAdapter.CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.rcv_item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindInfo(comments[position])
    }


    inner class CommentViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bindInfo(comment : Comment) {
            itemView.tv_name.text = comment.name
            itemView.tv_email.text = comment.email
            itemView.tv_text.text = comment.text
        }
    }
}