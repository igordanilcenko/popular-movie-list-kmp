package com.ihardanilchanka.sampleappkmp.domain.model

import kotlinx.datetime.LocalDate

data class RawMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: LocalDate?,
    val voteAverage: Double,
    val genreIds: List<Int>,
    val posterPath: String?,
    val backdropPath: String?,
)

private const val IMAGE_SIZE = "original"

fun RawMovie.toMovie(imageBaseUrl: String, genres: List<Genre>) = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    posterUrl = posterPath?.let { "$imageBaseUrl/$IMAGE_SIZE/$it" },
    backdropUrl = backdropPath?.let { "$imageBaseUrl/$IMAGE_SIZE/$it" },
    genreNames = genreIds.mapNotNull { id -> genres.find { it.id == id }?.name },
)
