package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.repository.MovieRepository

class MovieUseCase {
    class LoadSimilar(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendUseCase<Movie, List<Movie>> {
        override suspend fun invoke(arg: Movie) =
            mapToMovieList(movieRepository.loadSimilarMovieList(arg.id))
    }

    class LoadPopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.getPopularMovieList())
    }

    class RefreshPopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.refreshPopularMovieList())
    }

    class FetchMorePopular(
        private val movieRepository: MovieRepository,
        private val mapToMovieList: MovieConfigUseCase,
    ) : SuspendNoArgsUseCase<List<Movie>> {
        override suspend fun invoke() = mapToMovieList(movieRepository.fetchMorePopularMovies())
    }
}
