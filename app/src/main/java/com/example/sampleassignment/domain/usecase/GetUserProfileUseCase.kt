package com.example.sampleassignment.domain.usecase

import com.example.sampleassignment.domain.model.Album
import com.example.sampleassignment.domain.model.User
import com.example.sampleassignment.domain.repository.AlbumRepository
import com.example.sampleassignment.domain.repository.UserRepository
import javax.inject.Inject

data class UserProfile(
    val user: User,
    val albums: List<Album>
)

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val albumRepository: AlbumRepository
) {
    suspend operator fun invoke(userId: Int): Result<UserProfile> {
        val userResult = userRepository.getUserById(userId)
        if (userResult.isFailure) {
            return Result.failure(userResult.exceptionOrNull() ?: Exception("Failed to load user profile"))
        }

        val user = userResult.getOrThrow()
        val albumsResult = albumRepository.getUserAlbums(userId)
        val albums = albumsResult.getOrDefault(emptyList())

        return Result.success(UserProfile(user = user, albums = albums))
    }
}
