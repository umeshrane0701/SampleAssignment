package com.example.sampleassignment.domain.usecase

import androidx.paging.PagingData
import com.example.sampleassignment.domain.model.Photo
import com.example.sampleassignment.domain.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumPhotosUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    operator fun invoke(albumId: Int): Flow<PagingData<Photo>> {
        return albumRepository.getAlbumPhotosPager(albumId)
    }
}
