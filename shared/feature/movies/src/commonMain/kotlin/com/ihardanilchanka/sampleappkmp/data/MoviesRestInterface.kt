package com.ihardanilchanka.sampleappkmp.data

import com.ihardanilchanka.sampleappkmp.data.model.ConfigurationResponse
import com.ihardanilchanka.sampleappkmp.data.model.GenreListResponse
import com.ihardanilchanka.sampleappkmp.data.model.MovieListResponse
import com.ihardanilchanka.sampleappkmp.data.model.ReviewListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MoviesRestInterface(private val httpClient: HttpClient) {

    suspend fun getPopularMovieList(apiKey: String, page: Int): MovieListResponse =
        httpClient.get("movie/popular") {
            parameter("api_key", apiKey)
            parameter("page", page)
        }.body()

    suspend fun getMovieReviews(movieId: Int, apiKey: String): ReviewListResponse =
        httpClient.get("movie/$movieId/reviews") {
            parameter("api_key", apiKey)
        }.body()

    suspend fun getSimilarMovieList(movieId: Int, apiKey: String): MovieListResponse =
        httpClient.get("movie/$movieId/similar") {
            parameter("api_key", apiKey)
        }.body()

    suspend fun getConfiguration(apiKey: String): ConfigurationResponse =
        httpClient.get("configuration") {
            parameter("api_key", apiKey)
        }.body()

    suspend fun getGenreList(apiKey: String): GenreListResponse =
        httpClient.get("genre/movie/list") {
            parameter("api_key", apiKey)
        }.body()
}
