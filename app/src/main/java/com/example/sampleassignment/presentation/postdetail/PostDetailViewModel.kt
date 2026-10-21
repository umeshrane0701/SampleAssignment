package com.example.sampleassignment.presentation.postdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleassignment.domain.usecase.GetPostDetailsUseCase
import com.example.sampleassignment.domain.usecase.PostDetails
import com.example.sampleassignment.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostDetailsUseCase: GetPostDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId: Int = checkNotNull(savedStateHandle["postId"])

    private val _uiState = MutableStateFlow<UiState<PostDetails>>(UiState.Loading)
    val uiState: StateFlow<UiState<PostDetails>> = _uiState.asStateFlow()

    init {
        loadPostDetails()
    }

    fun loadPostDetails() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getPostDetailsUseCase(postId)
            result.fold(
                onSuccess = { postDetails ->
                    _uiState.value = UiState.Success(postDetails)
                },
                onFailure = { throwable ->
                    _uiState.value = UiState.Error(
                        throwable.localizedMessage ?: "Failed to load post details"
                    )
                }
            )
        }
    }
}
