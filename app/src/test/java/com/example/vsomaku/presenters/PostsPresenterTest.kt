package com.example.vsomaku.presenters

import android.content.Context
import com.example.vsomaku.data.Post
import com.example.vsomaku.presenters.views.PostsView
import com.example.vsomaku.presenters.views.`PostsView$$State`
import com.example.vsomaku.repos.PostRepo
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class PostsPresenterTest {

    @Test
    fun getPosts() {
        presenter.getPosts()

        verify(viewState).bindPosts(exPosts)
    }

    @Test
    fun getPagedList() {
        verify(viewState).bindPagedList(ArgumentMatchers.any())
    }

    companion object {

        private lateinit var repo : PostRepo
        private lateinit var context : Context
        private lateinit var view : PostsView
        private lateinit var viewState : `PostsView$$State`

        private lateinit var presenter : PostsPresenter

        private lateinit var successCaptor : ArgumentCaptor<Consumer<List<Post>>>
        private lateinit var errorCaptor : ArgumentCaptor<Consumer<Throwable>>

        private val exPosts = listOf(mock(Post::class.java))

        private fun provideRepoLogic() {
            `when`(repo.loadPosts(capture(successCaptor), capture(errorCaptor))).then {
                successCaptor.value.accept(exPosts)
            }

            `when`(repo.loadPosts(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), capture(successCaptor), capture(errorCaptor))).then {
                successCaptor.value.accept(exPosts)
            }
        }

        private fun initCaptors() {
            successCaptor = ArgumentCaptor.forClass(Consumer<List<Post>> {  }::class.java)
            errorCaptor = ArgumentCaptor.forClass(Consumer<Throwable> {  }::class.java)
        }

        @JvmStatic
        @BeforeClass
        fun init() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

            repo = mock(PostRepo::class.java)
            context = mock(Context::class.java)
            view = mock(PostsView::class.java)
            viewState = mock(`PostsView$$State`::class.java)

            presenter = PostsPresenter(repo, context)
            presenter.setViewState(viewState)

            initCaptors()
            provideRepoLogic()

            presenter.attachView(view)
        }

        @JvmStatic
        @AfterClass
        fun showLayout() {
            verify(viewState).showLayout()
        }
    }
}