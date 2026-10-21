package com.example.sampleassignment.domain.usecase

import androidx.paging.PagingData
import com.example.sampleassignment.domain.model.Post
import com.example.sampleassignment.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(): Flow<PagingData<Post>> {
        return postRepository.getPostsPager()
    }
}
