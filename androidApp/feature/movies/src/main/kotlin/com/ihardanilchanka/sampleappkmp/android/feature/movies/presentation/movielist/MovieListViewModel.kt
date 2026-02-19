package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.SelectedMovieUseCase
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Error
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Loading
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.LoadingState.Ready
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val selectMovie: SelectedMovieUseCase.Select,
    private val refreshPopularMovieList: MovieUseCase.RefreshPopular,
    private val fetchMorePopularMovies: MovieUseCase.FetchMorePopular,
    private val loadPopularMovies: MovieUseCase.LoadPopular,
) : ViewModel() {

    private val _movieListUiState = MutableStateFlow(MovieListUiState())
    val movieListUiState: StateFlow<MovieListUiState>
        get() = _movieListUiState.asStateFlow()

    init {
        if (movieListUiState.value.movies.isEmpty()) {
            loadInitialMovies()
        }
    }

    fun onMovieSelected(item: Movie) {
        selectMovie(item)
    }

    fun onReloadClicked() {
        fetchMoreMovies()
    }

    fun onNeedLoadMore() {
        fetchMoreMovies()
    }

    fun onSwipeToRefresh() {
        refreshMovies()
    }

    private fun loadInitialMovies() {
        viewModelScope.launch {
            _movieListUiState.value = _movieListUiState.value.copy(
                loadingState = Loading,
            )

            runCatching {
                val result = loadPopularMovies()
                if (result.isEmpty()) {
                    return@runCatching fetchMorePopularMovies()
                }
                result
            }.onSuccess { result ->
                _movieListUiState.value = _movieListUiState.value.copy(
                    movies = result,
                    loadingState = Ready
                )
            }.onFailure { error ->
                error.printStackTrace()
                _movieListUiState.value = _movieListUiState.value.copy(loadingState = Error(error))
            }
        }
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            _movieListUiState.value = _movieListUiState.value.copy(isRefreshing = true)

            runCatching {
                refreshPopularMovieList()
            }.onSuccess { result ->
                _movieListUiState.value = MovieListUiState(movies = result)
            }.onFailure { error ->
                error.printStackTrace()
                _movieListUiState.value = MovieListUiState(loadingState = Error(error))
            }
        }
    }

    private fun fetchMoreMovies() {
        if (canLoadMore()) {
            viewModelScope.launch {
                _movieListUiState.value = if (hasMoviesAlready()) {
                    _movieListUiState.value.copy(additionalLoadingState = Loading)
                } else {
                    _movieListUiState.value.copy(loadingState = Loading)
                }

                runCatching {
                    fetchMorePopularMovies()
                }.onSuccess { result ->
                    _movieListUiState.value = _movieListUiState.value.copy(
                        movies = result,
                        loadingState = Ready,
                        additionalLoadingState = Ready,
                    )
                }.onFailure { error ->
                    error.printStackTrace()
                    _movieListUiState.value = if (hasMoviesAlready()) {
                        _movieListUiState.value.copy(additionalLoadingState = Error(error))
                    } else {
                        _movieListUiState.value.copy(loadingState = Error(error))
                    }
                }
            }
        }
    }

    private fun canLoadMore() = movieListUiState.value.loadingState != Loading &&
            movieListUiState.value.additionalLoadingState != Loading

    private fun hasMoviesAlready() = _movieListUiState.value.movies.isNotEmpty()
}
