package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.ApiConfig
import com.ihardanilchanka.sampleappkmp.data.MoviesRestInterface
import com.ihardanilchanka.sampleappkmp.data.database.MovieEntity
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.database.SimilarMovieEntity
import com.ihardanilchanka.sampleappkmp.data.model.MovieDto
import com.ihardanilchanka.sampleappkmp.domain.model.Movie
import com.ihardanilchanka.sampleappkmp.domain.model.RawMovie
import com.ihardanilchanka.sampleappkmp.domain.repository.MovieRepository
import com.ihardanilchanka.sampleappkmp.simulateNetworkDelay
import kotlinx.datetime.LocalDate
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

class MovieRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val database: MoviesDatabase,
) : MovieRepository {

    private var selectedMovie: Movie? = null

    private var currentPage: Int = 1
    private val popularMoviesCache = mutableListOf<RawMovie>()

    private val similarMoviesCache = mutableMapOf<Int, List<RawMovie>>()

    override fun getSelectedMovie(): Movie =
        selectedMovie ?: error("No movie stored in the MovieRepository!")

    override fun storeSelectedMovie(movie: Movie) {
        selectedMovie = movie
    }

    override suspend fun loadSimilarMovieList(movieId: Int) = similarMoviesCache[movieId] ?: try {
        simulateNetworkDelay()

        val dtos = moviesRestInterface.getSimilarMovieList(movieId, ApiConfig.API_KEY).movies
        database.similarMovieQueries.deleteAll(movieId.toLong())
        dtos.forEachIndexed { index, dto ->
            insertSimilarMovie(movieId, dto, index)
        }
        dtos.map { it.toRawMovie() }
    } catch (e: IOException) {
        database.similarMovieQueries.getAll(movieId.toLong()).executeAsList()
            .takeIf { it.isNotEmpty() }
            ?.map { it.toRawMovie() }
            ?: throw e
    }.also { similarMoviesCache[movieId] = it }

    override fun getPopularMovieList() = popularMoviesCache

    override suspend fun refreshPopularMovieList(): List<RawMovie> {
        popularMoviesCache.clear()
        currentPage = 1

        simulateNetworkDelay()

        val dtos = moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, 1).movies

        database.movieQueries.deleteAll()
        dtos.forEachIndexed { index, dto -> insertMovie(dto, index) }

        popularMoviesCache.addAll(dtos.map { it.toRawMovie() })
        currentPage++

        return popularMoviesCache
    }

    override suspend fun fetchMorePopularMovies(): List<RawMovie> {
        simulateNetworkDelay()

        popularMoviesCache.addAll(
            try {
                val dtos =
                    moviesRestInterface.getPopularMovieList(ApiConfig.API_KEY, currentPage).movies
                if (currentPage == 1) {
                    database.movieQueries.deleteAll()
                    dtos.forEachIndexed { index, dto -> insertMovie(dto, index) }
                }
                dtos.map { it.toRawMovie() }
            } catch (e: IOException) {
                database.movieQueries.getAll().executeAsList()
                    .takeIf { currentPage == 1 && it.isNotEmpty() }
                    ?.map { it.toRawMovie() }
                    ?: throw e
            }
        )

        currentPage++
        return popularMoviesCache
    }

    private fun insertMovie(dto: MovieDto, order: Int) {
        database.movieQueries.insertMovie(
            id = dto.id.toLong(),
            posterPath = dto.posterPath,
            overview = dto.overview,
            releaseDate = dto.releaseDateStr,
            title = dto.title,
            backdropPath = dto.backdropPath,
            voteCount = dto.voteCount.toLong(),
            voteAverage = dto.voteAverage,
            genreIds = Json.encodeToString(dto.genreIds),
            sortOrder = order.toLong(),
        )
    }

    private fun insertSimilarMovie(movieId: Int, dto: MovieDto, order: Int) {
        database.similarMovieQueries.insertSimilarMovie(
            similarTo = movieId.toLong(),
            movieId = dto.id.toLong(),
            posterPath = dto.posterPath,
            overview = dto.overview,
            releaseDate = dto.releaseDateStr,
            title = dto.title,
            backdropPath = dto.backdropPath,
            voteCount = dto.voteCount.toLong(),
            voteAverage = dto.voteAverage,
            genreIds = Json.encodeToString(dto.genreIds),
            sortOrder = order.toLong(),
        )
    }
}

private fun MovieEntity.toRawMovie() = RawMovie(
    id = id.toInt(),
    title = title,
    overview = overview,
    releaseDate = releaseDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() },
    voteAverage = voteAverage,
    genreIds = Json.decodeFromString(genreIds),
    posterPath = posterPath,
    backdropPath = backdropPath,
)

private fun SimilarMovieEntity.toRawMovie() = RawMovie(
    id = movieId.toInt(),
    title = title,
    overview = overview,
    releaseDate = releaseDate?.let { runCatching { LocalDate.parse(it) }.getOrNull() },
    voteAverage = voteAverage,
    genreIds = Json.decodeFromString(genreIds),
    posterPath = posterPath,
    backdropPath = backdropPath,
)
