package com.example.sampleassignment.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sampleassignment.data.model.toDomain
import com.example.sampleassignment.data.remote.JsonPlaceholderApi
import com.example.sampleassignment.domain.model.Post

class PostsPagingSource(
    private val api: JsonPlaceholderApi
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: 1
        return try {
            val response = api.getPosts(page = page, limit = params.loadSize)
            val posts = response.map { it.toDomain() }
            LoadResult.Page(
                data = posts,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (posts.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
