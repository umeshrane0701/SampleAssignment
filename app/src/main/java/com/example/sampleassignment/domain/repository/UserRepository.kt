package com.example.sampleassignment.domain.repository

import com.example.sampleassignment.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: Int): Result<User>
}
