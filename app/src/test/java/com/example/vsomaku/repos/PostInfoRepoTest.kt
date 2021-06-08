package com.example.vsomaku.repos

import com.example.vsomaku.daos.CommentDao
import com.example.vsomaku.daos.UserDao
import com.example.vsomaku.data.Comment
import com.example.vsomaku.data.User
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response

class PostInfoRepoTest : BaseRepoTest() {

    @InjectMocks
    private lateinit var repo : PostInfoRepo

    @Mock
    private lateinit var userDao : UserDao
    @Mock
    private lateinit var commentDao: CommentDao

    @Mock
    private lateinit var usersResponse : Response<List<User>>
    @Mock
    private lateinit var commentsResponse : Response<List<Comment>>

    @Mock
    private lateinit var successUsersConsumer : Consumer<User>
    @Mock
    private lateinit var successCommentsConsumer : Consumer<List<Comment>>

    private val postId = 0
    private val userId = 0

    @Test
    fun loadComments() {
        val expectedComments
                = arrayListOf(Comment(postId, 0, "Comment name", "User email", "Some text"))

        Mockito.`when`(api.getComments(postId)).thenReturn(Single.just(commentsResponse))
        Mockito.`when`(commentsResponse.body()).thenReturn(expectedComments)

        repo.loadComments(successCommentsConsumer, errorConsumer, postId)

        verify(api).getComments(postId)
        verify(commentsResponse, times(2)).body()
        verify(commentDao).insertAll(expectedComments)
        verify(successCommentsConsumer).accept(expectedComments)
    }

    @Test
    fun loadUserInfo() {
        val expectedUsers = arrayListOf(User(userId, "User", "Name", "some@email"))

        Mockito.`when`(api.getUser(userId)).thenReturn(Single.just(usersResponse))
        Mockito.`when`(usersResponse.body()).thenReturn(expectedUsers)

        repo.loadUserInfo(successUsersConsumer, errorConsumer, userId)

        verify(api).getUser(userId)
        verify(usersResponse).body()
        verify(userDao).insert(expectedUsers[0])
        verify(successUsersConsumer).accept(expectedUsers[0])
    }
}