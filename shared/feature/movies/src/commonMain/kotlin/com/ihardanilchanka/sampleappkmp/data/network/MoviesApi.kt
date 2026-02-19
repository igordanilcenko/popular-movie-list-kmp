package com.ihardanilchanka.sampleappkmp.data.network

import com.ihardanilchanka.sampleappkmp.data.model.ConfigurationResponse
import com.ihardanilchanka.sampleappkmp.data.model.GenreListResponse
import com.ihardanilchanka.sampleappkmp.data.model.MovieListResponse
import com.ihardanilchanka.sampleappkmp.data.model.ReviewListResponse

interface MoviesApi {
    suspend fun getPopularMovieList(apiKey: String, page: Int): MovieListResponse
    suspend fun getMovieReviews(movieId: Int, apiKey: String): ReviewListResponse
    suspend fun getSimilarMovieList(movieId: Int, apiKey: String): MovieListResponse
    suspend fun getConfiguration(apiKey: String): ConfigurationResponse
    suspend fun getGenreList(apiKey: String): GenreListResponse
}
