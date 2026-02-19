package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.data.model.GenreDto
import com.ihardanilchanka.sampleappkmp.data.model.ImageConfigDto
import com.ihardanilchanka.sampleappkmp.data.model.MovieDto
import com.ihardanilchanka.sampleappkmp.data.model.ReviewDto

fun fakeMovieDto(id: Int = 1) = MovieDto(
    id = id,
    posterPath = null,
    overview = "Overview $id",
    releaseDateStr = null,
    title = "Movie $id",
    backdropPath = null,
    voteCount = 100,
    voteAverage = 7.0,
    genreIds = listOf(28),
)

fun fakeReviewDto(id: String = "r1") = ReviewDto(
    id = id,
    author = "Author $id",
    content = "Great movie!",
)

fun fakeGenreDto(id: Int = 1) = GenreDto(id = id, name = "Genre $id")

fun fakeImageConfigDto() = ImageConfigDto(
    baseUrl = "http://img.tmdb.org/",
    secureBaseUrl = "https://img.tmdb.org/",
)
