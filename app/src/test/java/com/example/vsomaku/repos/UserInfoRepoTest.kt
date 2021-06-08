package com.example.vsomaku.repos

import com.example.vsomaku.daos.AlbumDao
import com.example.vsomaku.daos.PhotoDao
import com.example.vsomaku.data.Album
import com.example.vsomaku.data.Photo
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Test
import org.mockito.*
import retrofit2.Response

class UserInfoRepoTest : BaseRepoTest() {

    @Mock
    private lateinit var albumDao : AlbumDao
    @Mock
    private lateinit var photoDao: PhotoDao

    @Mock
    private lateinit var albumsResponse : Response<List<Album>>
    @Mock
    private lateinit var photosReponse : Response<List<Photo>>

    @Mock
    private lateinit var successConsumer : Consumer<Pair<List<Album>, List<Photo>>>

    @InjectMocks
    private lateinit var repo : UserInfoRepo

    private val userId = 0

    @Test
    fun loadAlbumsData() {
        val expectedAlbums = arrayListOf(Album(userId, 0))
        val expectedPhotos = arrayListOf(Photo(expectedAlbums.first().id, 0))
        val expectedPair = expectedAlbums to expectedPhotos

        Mockito.`when`(api.getAlbums(userId)).thenReturn(Single.just(albumsResponse))
        Mockito.`when`(albumsResponse.body()).thenReturn(expectedAlbums)
        Mockito.`when`(api.getPhotos(expectedAlbums.first().id)).thenReturn(Single.just(photosReponse))
        Mockito.`when`(photosReponse.body()).thenReturn(expectedPhotos)

        repo.loadAlbumsData(successConsumer, errorConsumer, userId)

        try {
            Thread.sleep(5000)
        } catch (exception : InterruptedException) {
            exception.printStackTrace()
        }

        verify(api).getAlbums(userId)
        verify(albumsResponse, times(2)).body()
        verify(albumDao).insertAll(expectedAlbums)
        verify(photoDao).getPhotosByAlbumId(expectedAlbums.first().id)
        verify(photosReponse, times(2)).body()
        verify(photoDao).insertAll(expectedPhotos)
        verify(successConsumer).accept(expectedPair)
    }
}