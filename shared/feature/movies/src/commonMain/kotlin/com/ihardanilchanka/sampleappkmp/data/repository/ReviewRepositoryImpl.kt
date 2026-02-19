package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.ApiConfig
import com.ihardanilchanka.sampleappkmp.data.MoviesRestInterface
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.domain.model.Review
import com.ihardanilchanka.sampleappkmp.domain.repository.ReviewRepository
import com.ihardanilchanka.sampleappkmp.simulateNetworkDelay
import kotlinx.io.IOException

class ReviewRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val database: MoviesDatabase,
) : ReviewRepository {
    private val reviewsCache = mutableMapOf<Int, List<Review>>()

    override suspend fun loadReviewList(movieId: Int) = reviewsCache[movieId] ?: try {
        simulateNetworkDelay()

        val dtos = moviesRestInterface.getMovieReviews(movieId, ApiConfig.API_KEY).reviews
        database.reviewQueries.deleteAll(movieId.toLong())
        dtos.forEachIndexed { index, dto ->
            database.reviewQueries.insertReview(
                movieId = movieId.toLong(),
                id = dto.id,
                author = dto.author,
                content = dto.content,
                sortOrder = index.toLong(),
            )
        }
        dtos.map { it.toModel() }
    } catch (e: IOException) {
        database.reviewQueries.getAll(movieId.toLong()).executeAsList()
            .takeIf { it.isNotEmpty() }
            ?.map { Review(id = it.id, author = it.author, content = it.content) }
            ?: throw e
    }.also { reviewsCache[movieId] = it }
}
