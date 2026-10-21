package com.example.sampleassignment.di

import com.example.sampleassignment.data.repository.AlbumRepositoryImpl
import com.example.sampleassignment.data.repository.PostRepositoryImpl
import com.example.sampleassignment.data.repository.UserRepositoryImpl
import com.example.sampleassignment.domain.repository.AlbumRepository
import com.example.sampleassignment.domain.repository.PostRepository
import com.example.sampleassignment.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAlbumRepository(
        albumRepositoryImpl: AlbumRepositoryImpl
    ): AlbumRepository
}
