package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.usecase.ShowMovieDetailUseCase
import com.ihardanilchanka.sampleappkmp.domain.repository.MovieRepository

class SelectedMovieUseCase {
    class Load(
        private val movieRepository: MovieRepository,
    ) : NoArgsUseCase<Movie> {
        override fun invoke() = movieRepository.getSelectedMovie()
    }

    class Select(
        private val movieRepository: MovieRepository,
        private val showMovieDetail: ShowMovieDetailUseCase,
    ) : UseCase<Movie, Unit> {
        override fun invoke(arg: Movie) {
            movieRepository.storeSelectedMovie(arg)
            showMovieDetail()
        }
    }
}
