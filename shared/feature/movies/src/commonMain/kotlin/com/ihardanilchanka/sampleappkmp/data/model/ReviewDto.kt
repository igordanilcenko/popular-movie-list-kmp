package com.ihardanilchanka.sampleappkmp.data.model

import com.ihardanilchanka.sampleappkmp.domain.model.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("id") val id: String,
    @SerialName("author") val author: String,
    @SerialName("content") val content: String,
) {

    fun toModel() = Review(
        id = id,
        author = author,
        content = content,
    )
}
