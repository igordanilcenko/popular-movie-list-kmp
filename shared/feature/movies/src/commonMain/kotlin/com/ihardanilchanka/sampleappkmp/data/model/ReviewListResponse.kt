package com.ihardanilchanka.sampleappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListResponse(
    @SerialName("id") val id: Int,
    @SerialName("results") val reviews: List<ReviewDto>,
)
