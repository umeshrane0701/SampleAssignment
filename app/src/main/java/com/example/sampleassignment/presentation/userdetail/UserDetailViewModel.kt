package com.example.sampleassignment.presentation.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleassignment.domain.usecase.GetUserProfileUseCase
import com.example.sampleassignment.domain.usecase.UserProfile
import com.example.sampleassignment.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle["userId"])

    private val _uiState = MutableStateFlow<UiState<UserProfile>>(UiState.Loading)
    val uiState: StateFlow<UiState<UserProfile>> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = getUserProfileUseCase(userId)
            result.fold(
                onSuccess = { userProfile ->
                    _uiState.value = UiState.Success(userProfile)
                },
                onFailure = { throwable ->
                    _uiState.value = UiState.Error(
                        throwable.localizedMessage ?: "Failed to load user profile"
                    )
                }
            )
        }
    }
}
