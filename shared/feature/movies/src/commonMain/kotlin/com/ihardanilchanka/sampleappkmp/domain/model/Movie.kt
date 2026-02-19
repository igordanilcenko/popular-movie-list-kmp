package com.ihardanilchanka.sampleappkmp.domain.model

import kotlinx.datetime.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genreNames: List<String>,
)
