package com.example.sampleassignment.presentation.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Posts : Screen("posts")

    data object PostDetail : Screen("post/{postId}") {
        fun createRoute(postId: Int): String = "post/$postId"
    }

    data object UserDetail : Screen("user/{userId}") {
        fun createRoute(userId: Int): String = "user/$userId"
    }

    data object AlbumPhotos : Screen("album/{albumId}?albumTitle={albumTitle}") {
        fun createRoute(albumId: Int, albumTitle: String): String {
            val encodedTitle = Uri.encode(albumTitle)
            return "album/$albumId?albumTitle=$encodedTitle"
        }
    }
}
