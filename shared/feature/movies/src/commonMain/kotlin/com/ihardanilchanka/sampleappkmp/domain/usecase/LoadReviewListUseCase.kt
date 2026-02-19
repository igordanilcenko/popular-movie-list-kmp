package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.Review
import com.ihardanilchanka.sampleappkmp.domain.repository.ReviewRepository

class LoadReviewListUseCase(
    private val reviewRepository: ReviewRepository,
) : SuspendUseCase<Movie, List<Review>> {
    override suspend fun invoke(arg: Movie): List<Review> {
        return reviewRepository.loadReviewList(movieId = arg.id)
    }
}
