package com.example.sampleassignment.presentation.albumphotos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sampleassignment.domain.model.Photo
import com.example.sampleassignment.domain.usecase.GetAlbumPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AlbumPhotosViewModel @Inject constructor(
    getAlbumPhotosUseCase: GetAlbumPhotosUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumId: Int = checkNotNull(savedStateHandle["albumId"])
    val albumTitle: String = savedStateHandle.get<String>("albumTitle") ?: "Album Photos"

    val photosPagingFlow: Flow<PagingData<Photo>> =
        getAlbumPhotosUseCase(albumId)
            .cachedIn(viewModelScope)
}
