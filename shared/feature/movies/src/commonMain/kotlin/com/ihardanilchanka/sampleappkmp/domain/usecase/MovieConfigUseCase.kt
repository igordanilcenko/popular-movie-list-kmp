package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.RawMovie
import com.ihardanilchanka.sampleappkmp.domain.model.toMovie

class MovieConfigUseCase(
    private val loadConfig: LoadConfigUseCase,
    private val loadGenreList: LoadGenreListUseCase,
) : SuspendUseCase<List<RawMovie>, List<Movie>> {
    override suspend fun invoke(arg: List<RawMovie>): List<Movie> {
        val config = loadConfig()
        val genres = loadGenreList()
        return arg.map { it.toMovie(config.secureBaseUrl, genres) }
    }
}
