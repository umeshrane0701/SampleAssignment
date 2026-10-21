package com.example.sampleassignment.presentation.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sampleassignment.domain.model.Post
import com.example.sampleassignment.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    val postsPagingFlow: Flow<PagingData<Post>> =
        getPostsUseCase()
            .cachedIn(viewModelScope)
}
