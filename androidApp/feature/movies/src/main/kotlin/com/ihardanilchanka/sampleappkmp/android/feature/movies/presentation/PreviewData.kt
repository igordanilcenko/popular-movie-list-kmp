package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.Review
import kotlinx.datetime.LocalDate

internal val previewMovie = Movie(
    id = 1,
    title = "The Dark Knight",
    overview = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
    releaseDate = LocalDate(2008, 7, 21),
    voteAverage = 8.5,
    posterUrl = null,
    backdropUrl = null,
    genreNames = listOf("Action", "Crime", "Drama"),
)

internal val previewMovies = listOf(
    previewMovie,
    previewMovie.copy(
        id = 2,
        title = "Inception",
        voteAverage = 8.8,
        genreNames = listOf("Sci-Fi", "Action")
    ),
    previewMovie.copy(
        id = 3,
        title = "Interstellar",
        voteAverage = 8.6,
        genreNames = listOf("Sci-Fi", "Adventure")
    ),
    previewMovie.copy(id = 4, title = "The Prestige", voteAverage = 8.5),
)

internal val previewReviewShort = Review(
    id = "1",
    author = "MovieFan123",
    content = "Amazing movie! One of the best superhero films ever made.",
)

internal val previewReviewLong = Review(
    id = "2",
    author = "CinematicCritic",
    content = "Heath Ledger's performance as the Joker is nothing short of legendary. The way he embodies chaos and anarchy while maintaining a twisted sense of humor makes this one of the greatest villain portrayals in cinema history. Christopher Nolan crafted a masterpiece that transcends the superhero genre.",
)
