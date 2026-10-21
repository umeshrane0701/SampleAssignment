package com.example.sampleassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sampleassignment.data.model.toDomain
import com.example.sampleassignment.data.paging.PhotosPagingSource
import com.example.sampleassignment.data.remote.JsonPlaceholderApi
import com.example.sampleassignment.domain.model.Album
import com.example.sampleassignment.domain.model.Photo
import com.example.sampleassignment.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val api: JsonPlaceholderApi
) : AlbumRepository {

    override suspend fun getUserAlbums(userId: Int): Result<List<Album>> {
        return try {
            val dtos = api.getUserAlbums(userId)
            Result.success(dtos.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAlbumPhotosPager(albumId: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotosPagingSource(api, albumId) }
        ).flow
    }
}
