package com.example.sampleassignment.data.remote

import com.example.sampleassignment.data.model.AlbumDto
import com.example.sampleassignment.data.model.PhotoDto
import com.example.sampleassignment.data.model.PostDto
import com.example.sampleassignment.data.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderApi {

    @GET("posts")
    suspend fun getPosts(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<PostDto>

    @GET("posts/{postId}")
    suspend fun getPostById(
        @Path("postId") postId: Int
    ): PostDto

    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Int
    ): UserDto

    @GET("users/{userId}/albums")
    suspend fun getUserAlbums(
        @Path("userId") userId: Int
    ): List<AlbumDto>

    @GET("albums/{albumId}/photos")
    suspend fun getAlbumPhotos(
        @Path("albumId") albumId: Int,
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<PhotoDto>
}
