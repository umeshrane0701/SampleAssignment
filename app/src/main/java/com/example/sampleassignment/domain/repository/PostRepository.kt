package com.example.sampleassignment.domain.repository

import androidx.paging.PagingData
import com.example.sampleassignment.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsPager(): Flow<PagingData<Post>>
    suspend fun getPostById(postId: Int): Result<Post>
}
