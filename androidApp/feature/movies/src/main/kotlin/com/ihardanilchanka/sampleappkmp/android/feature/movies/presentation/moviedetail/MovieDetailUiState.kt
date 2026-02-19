package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.Review
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Ready

data class MovieDetailUiState(
    val movie: Movie? = null,
    val reviewsUiState: ReviewsUiState = ReviewsUiState(),
    val similarMoviesUiState: SimilarMoviesUiState = SimilarMoviesUiState(),
)

data class SimilarMoviesUiState(
    val similarMovies: List<Movie>? = null,
    val loadingState: LoadingState = Ready,
)

data class ReviewsUiState(
    val reviews: List<Review>? = null,
    val loadingState: LoadingState = Ready,
)
