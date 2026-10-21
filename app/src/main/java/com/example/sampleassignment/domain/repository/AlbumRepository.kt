package com.example.sampleassignment.domain.repository

import androidx.paging.PagingData
import com.example.sampleassignment.domain.model.Album
import com.example.sampleassignment.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    suspend fun getUserAlbums(userId: Int): Result<List<Album>>
    fun getAlbumPhotosPager(albumId: Int): Flow<PagingData<Photo>>
}
