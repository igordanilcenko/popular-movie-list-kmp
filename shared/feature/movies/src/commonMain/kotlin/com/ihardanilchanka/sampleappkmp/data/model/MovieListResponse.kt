package com.ihardanilchanka.sampleappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val movies: List<MovieDto>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int,
)
