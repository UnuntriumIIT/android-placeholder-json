package com.example.vsomaku.repos

import com.example.vsomaku.daos.PostDao
import com.example.vsomaku.data.Post
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class PostRepoTest : BaseRepoTest() {

    @Mock
    private lateinit var dao : PostDao
    @Mock
    private lateinit var postsResponse : Response<List<Post>>
    @Mock
    private lateinit var successConsumer : Consumer<List<Post>>

    @InjectMocks
    private lateinit var repo : PostRepo

    @Test
    fun testLoadPosts() {
        val expectedPosts = arrayListOf(Post(0, 0, "Post", "Some description"))

        Mockito.`when`(api.getPosts()).thenReturn(Single.just(postsResponse))
        Mockito.`when`(postsResponse.body()).thenReturn(expectedPosts)

        repo.loadPosts(successConsumer, errorConsumer)

        verify(api).getPosts()
        verify(dao).insertAll(expectedPosts)
        verify(successConsumer).accept(expectedPosts)
    }
}