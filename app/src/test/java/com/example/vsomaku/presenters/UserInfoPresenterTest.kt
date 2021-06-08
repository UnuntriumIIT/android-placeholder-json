package com.example.vsomaku.presenters

import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.example.vsomaku.data.User
import com.example.vsomaku.presenters.views.UserView
import com.example.vsomaku.presenters.views.`UserView$$State`
import com.example.vsomaku.repos.UserInfoRepo
import com.nhaarman.mockitokotlin2.capture
import io.reactivex.functions.Consumer
import org.junit.AfterClass
import org.mockito.Mockito.*
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.ArgumentCaptor

class UserInfoPresenterTest {

    @Test
    fun showUserInfo() {
        verify(viewState).showUserInfo(user)
    }

    @Test
    fun getAlbumsData() {
        verify(viewState).bindAlbumsInfo(exAlbums, exPhotos)
    }

    companion object {

        private lateinit var view : UserView
        private lateinit var viewState : `UserView$$State`

        private lateinit var user : User

        private lateinit var repo : UserInfoRepo
        private lateinit var presenter : UserInfoPresenter

        private val exAlbums = listOf(mock(Album::class.java))
        private val exPhotos = listOf(mock(Photo::class.java))
        private val exPair = exAlbums to exPhotos

        private lateinit var successCaptor : ArgumentCaptor<Consumer<Pair<List<Album>, List<Photo>>>>
        private lateinit var errorCaptor : ArgumentCaptor<Consumer<Throwable>>
        private lateinit var idCaptor : ArgumentCaptor<Int>

        private fun provideRepoLogic() {
            `when`(repo.loadAlbumsData(capture(successCaptor), capture(errorCaptor), capture(idCaptor))).then {
                successCaptor.value.accept(exPair)
            }
        }

        private fun initCaptors() {
            successCaptor = ArgumentCaptor.forClass(Consumer<Pair<List<Album>, List<Photo>>> {  }::class.java)
            errorCaptor = ArgumentCaptor.forClass(Consumer<Throwable> {  }::class.java)
            idCaptor = ArgumentCaptor.forClass(Int::class.java)
        }

        @JvmStatic
        @BeforeClass
        fun init() {
            user = mock(User::class.java)
            view = mock(UserView::class.java)
            viewState = mock(`UserView$$State`::class.java)
            repo = mock(UserInfoRepo::class.java)

            initCaptors()
            provideRepoLogic()

            presenter = UserInfoPresenter(repo)
            presenter.attachUser(user)
            presenter.setViewState(viewState)
            presenter.attachView(view)
        }

        @JvmStatic
        @AfterClass
        fun showLayout() {
            verify(viewState).showLayout()
        }

    }
}