package com.example.sampleassignment.data.model

import com.example.sampleassignment.domain.model.Photo
import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("albumId") val albumId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?
)

fun PhotoDto.toDomain(): Photo {
    return Photo(
        id = id ?: 0,
        albumId = albumId ?: 0,
        title = title.orEmpty(),
        url = url.orEmpty(),
        thumbnailUrl = thumbnailUrl.orEmpty()
    )
}
