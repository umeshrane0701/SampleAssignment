package com.example.sampleassignment.presentation.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.sampleassignment.domain.model.Post
import com.example.sampleassignment.presentation.common.AppTopBar
import com.example.sampleassignment.presentation.common.EmptyView
import com.example.sampleassignment.presentation.common.ErrorView
import com.example.sampleassignment.presentation.common.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    onPostClick: (Int) -> Unit,
    viewModel: PostsViewModel = hiltViewModel()
) {
    val posts: LazyPagingItems<Post> = viewModel.postsPagingFlow.collectAsLazyPagingItems()
    val isRefreshing = posts.loadState.refresh is LoadState.Loading && posts.itemCount > 0

    Scaffold(
        topBar = {
            AppTopBar(title = "Posts")
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { posts.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val refreshState = posts.loadState.refresh) {
                is LoadState.Loading -> {
                    if (posts.itemCount == 0) {
                        LoadingView(message = "Loading posts...")
                    } else {
                        PostsList(posts = posts, onPostClick = onPostClick)
                    }
                }
                is LoadState.Error -> {
                    if (posts.itemCount == 0) {
                        ErrorView(
                            errorMessage = refreshState.error.localizedMessage ?: "Failed to load posts",
                            onRetry = { posts.retry() }
                        )
                    } else {
                        PostsList(posts = posts, onPostClick = onPostClick)
                    }
                }
                is LoadState.NotLoading -> {
                    if (posts.itemCount == 0) {
                        EmptyView(message = "No posts found")
                    } else {
                        PostsList(posts = posts, onPostClick = onPostClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun PostsList(
    posts: LazyPagingItems<Post>,
    onPostClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = posts.itemCount,
            key = { index -> posts[index]?.id ?: index }
        ) { index ->
            val post = posts[index]
            if (post != null) {
                PostItemCard(
                    post = post,
                    onClick = { onPostClick(post.id) }
                )
            }
        }

        when (val appendState = posts.loadState.append) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            is LoadState.Error -> {
                item {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error loading more posts",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { posts.retry() }) {
                                Text(text = "Retry")
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
private fun PostItemCard(
    post: Post,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
