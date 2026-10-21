package com.example.sampleassignment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.sampleassignment.data.model.toDomain
import com.example.sampleassignment.data.paging.PostsPagingSource
import com.example.sampleassignment.data.remote.JsonPlaceholderApi
import com.example.sampleassignment.domain.model.Post
import com.example.sampleassignment.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: JsonPlaceholderApi
) : PostRepository {

    override fun getPostsPager(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostsPagingSource(api) }
        ).flow
    }

    override suspend fun getPostById(postId: Int): Result<Post> {
        return try {
            val dto = api.getPostById(postId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
