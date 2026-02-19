package com.ihardanilchanka.sampleappkmp.android.feature.movies

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.Review
import kotlinx.datetime.LocalDate

fun fakeMovie(id: Int = 1) = Movie(
    id = id,
    title = "Movie $id",
    overview = "Overview $id",
    releaseDate = LocalDate(2024, 1, 1),
    voteAverage = 7.5,
    posterUrl = null,
    backdropUrl = null,
    genreNames = listOf("Action"),
)

fun fakeReview(id: String = "1") = Review(
    id = id,
    author = "Author $id",
    content = "Great movie!",
)
