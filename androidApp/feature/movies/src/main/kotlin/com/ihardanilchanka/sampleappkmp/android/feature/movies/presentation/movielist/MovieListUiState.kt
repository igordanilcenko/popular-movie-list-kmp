package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Ready

data class MovieListUiState(
    val movies: List<Movie> = mutableListOf(),
    val isRefreshing: Boolean = false,
    val loadingState: LoadingState = Ready,
    val additionalLoadingState: LoadingState = Ready,
)
