package com.example.sampleassignment.data.repository

import com.example.sampleassignment.data.model.toDomain
import com.example.sampleassignment.data.remote.JsonPlaceholderApi
import com.example.sampleassignment.domain.model.User
import com.example.sampleassignment.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: JsonPlaceholderApi
) : UserRepository {

    override suspend fun getUserById(userId: Int): Result<User> {
        return try {
            val dto = api.getUserById(userId)
            Result.success(dto.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
