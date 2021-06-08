package com.example.vsomaku.presenters.views

import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.data.Comment

interface PostInfoView : BaseView  {
    fun bindComments(comments : List<Comment>)
    fun bindUserInfo(user : User)
    fun bindPostInfo(post : Post)
}