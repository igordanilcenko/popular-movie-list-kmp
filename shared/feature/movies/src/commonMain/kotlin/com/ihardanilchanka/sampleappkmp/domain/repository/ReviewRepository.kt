package com.ihardanilchanka.sampleappkmp.domain.repository

import com.ihardanilchanka.sampleappkmp.domain.model.Review

interface ReviewRepository {
    suspend fun loadReviewList(movieId: Int): List<Review>
}
