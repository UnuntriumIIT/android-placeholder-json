package com.example.vsomaku.presenters

import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.Post
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.PostInfoView
import com.example.vsomaku.presenters.views.`PostInfoView$$State`
import com.example.vsomaku.repos.PostInfoRepo
import com.nhaarman.mockitokotlin2.capture
import io.reactivex.functions.Consumer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostInfoPresenterTest {
    @Test
    fun showPostInfo() {
        verify(viewState).bindPostInfo(post)
    }

    @Test
    fun getComments() {
        verify(viewState).bindComments(exComments)
    }

    @Test
    fun getUserInfo() {
        verify(viewState).bindUserInfo(user)
    }

    companion object {
        private lateinit var viewState : `PostInfoView$$State`
        private lateinit var repo : PostInfoRepo
        private lateinit var view : PostInfoView
        private lateinit var user : User

        private lateinit var post : Post
        private lateinit var presenter : PostInfoPresenter

        private val exComments = listOf(mock(Comment::class.java))

        private lateinit var commentsCaptor : ArgumentCaptor<Consumer<List<Comment>>>
        private lateinit var userCaptor : ArgumentCaptor<Consumer<User>>
        private lateinit var idCaptor : ArgumentCaptor<Int>
        private lateinit var errorCaptor : ArgumentCaptor<Consumer<Throwable>>

        @JvmStatic
        @BeforeClass
        fun init() {
            viewState = mock(`PostInfoView$$State`::class.java)
            repo = mock(PostInfoRepo::class.java)
            view = mock(PostInfoView::class.java)
            user = mock(User::class.java)
            post = mock(Post::class.java)

            presenter = PostInfoPresenter(repo)
            presenter.attachPost(post)
            presenter.setViewState(viewState)

            initCaptors()
            provideRepoLogic()

            presenter.attachView(view)
        }

        private fun initCaptors() {
            commentsCaptor = ArgumentCaptor.forClass(Consumer<List<Comment>> {  }::class.java)
            userCaptor = ArgumentCaptor.forClass(Consumer<User> {  }::class.java)
            idCaptor = ArgumentCaptor.forClass(Int::class.java)
            errorCaptor = ArgumentCaptor.forClass(Consumer<Throwable> {  }::class.java)
        }

        private fun provideRepoLogic() {
            `when`(repo.loadComments(capture(commentsCaptor), capture(errorCaptor), capture(idCaptor))).then {
                commentsCaptor.value.accept(exComments)
            }
            `when`(repo.loadUserInfo(capture(userCaptor), capture(errorCaptor), capture(idCaptor))).then {
                userCaptor.value.accept(user)
            }
        }

        @JvmStatic
        @AfterClass
        fun showLayout() {
            verify(viewState).showLayout()
        }
    }
}