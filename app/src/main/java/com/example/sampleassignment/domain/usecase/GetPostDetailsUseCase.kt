package com.example.sampleassignment.domain.usecase

import com.example.sampleassignment.domain.model.Post
import com.example.sampleassignment.domain.model.User
import com.example.sampleassignment.domain.repository.PostRepository
import com.example.sampleassignment.domain.repository.UserRepository
import javax.inject.Inject

data class PostDetails(
    val post: Post,
    val author: User?
)

class GetPostDetailsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(postId: Int): Result<PostDetails> {
        val postResult = postRepository.getPostById(postId)
        if (postResult.isFailure) {
            return Result.failure(postResult.exceptionOrNull() ?: Exception("Failed to load post"))
        }

        val post = postResult.getOrThrow()
        val authorResult = userRepository.getUserById(post.userId)
        val author = authorResult.getOrNull()

        return Result.success(PostDetails(post = post, author = author))
    }
}
