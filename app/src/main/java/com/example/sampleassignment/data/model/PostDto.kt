package com.example.sampleassignment.data.model

import com.example.sampleassignment.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("userId") val userId: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?
)

fun PostDto.toDomain(): Post {
    return Post(
        id = id ?: 0,
        userId = userId ?: 0,
        title = title.orEmpty(),
        body = body.orEmpty()
    )
}
