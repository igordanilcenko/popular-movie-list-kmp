package com.ihardanilchanka.sampleappkmp.domain.repository

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.RawMovie

interface MovieRepository {
    fun getSelectedMovie(): Movie
    fun storeSelectedMovie(movie: Movie)
    suspend fun loadSimilarMovieList(movieId: Int): List<RawMovie>
    fun getPopularMovieList(): List<RawMovie>
    suspend fun refreshPopularMovieList(): List<RawMovie>
    suspend fun fetchMorePopularMovies(): List<RawMovie>
}
