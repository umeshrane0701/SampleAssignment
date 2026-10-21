package com.example.sampleassignment.data.model

import com.example.sampleassignment.domain.model.Album
import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("userId") val userId: Int?,
    @SerializedName("title") val title: String?
)

fun AlbumDto.toDomain(): Album {
    return Album(
        id = id ?: 0,
        userId = userId ?: 0,
        title = title.orEmpty()
    )
}
