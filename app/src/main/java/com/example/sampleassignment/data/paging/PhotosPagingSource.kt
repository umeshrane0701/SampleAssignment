package com.example.sampleassignment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sampleassignment.data.model.toDomain
import com.example.sampleassignment.data.remote.JsonPlaceholderApi
import com.example.sampleassignment.domain.model.Photo

class PhotosPagingSource(
    private val api: JsonPlaceholderApi,
    private val albumId: Int
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        return try {
            val response = api.getAlbumPhotos(albumId = albumId, page = page, limit = params.loadSize)
            val photos = response.map { it.toDomain() }
            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
