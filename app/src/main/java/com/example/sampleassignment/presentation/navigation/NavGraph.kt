package com.example.sampleassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sampleassignment.presentation.albumphotos.AlbumPhotosScreen
import com.example.sampleassignment.presentation.postdetail.PostDetailScreen
import com.example.sampleassignment.presentation.posts.PostsScreen
import com.example.sampleassignment.presentation.userdetail.UserDetailScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Posts.route,
        modifier = modifier
    ) {
        composable(route = Screen.Posts.route) {
            PostsScreen(
                onPostClick = { postId ->
                    navController.navigate(Screen.PostDetail.createRoute(postId))
                }
            )
        }

        composable(
            route = Screen.PostDetail.route,
            arguments = listOf(
                navArgument("postId") { type = NavType.IntType }
            )
        ) {
            PostDetailScreen(
                onBackClick = { navController.popBackStack() },
                onAuthorClick = { userId ->
                    navController.navigate(Screen.UserDetail.createRoute(userId))
                }
            )
        }

        composable(
            route = Screen.UserDetail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) {
            UserDetailScreen(
                onBackClick = { navController.popBackStack() },
                onAlbumClick = { albumId, albumTitle ->
                    navController.navigate(Screen.AlbumPhotos.createRoute(albumId, albumTitle))
                }
            )
        }

        composable(
            route = Screen.AlbumPhotos.route,
            arguments = listOf(
                navArgument("albumId") { type = NavType.IntType },
                navArgument("albumTitle") {
                    type = NavType.StringType
                    defaultValue = "Album Photos"
                }
            )
        ) {
            AlbumPhotosScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
