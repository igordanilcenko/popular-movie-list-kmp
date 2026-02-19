package com.ihardanilchanka.sampleappkmp.data.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.model.MovieListResponse
import com.ihardanilchanka.sampleappkmp.data.network.MoviesApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MovieRepositoryTest {

    private val api = mockk<MoviesApi>()
    private lateinit var database: MoviesDatabase

    @Before
    fun setUp() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MoviesDatabase.Schema.create(driver)
        database = MoviesDatabase(driver)
    }

    private fun createRepo() = MovieRepositoryImpl(api, database)

    @Test
    fun `loadSimilarMovieList uses memory cache and skips network on second call for same id`() =
        runTest {
            val repo = createRepo()
            coEvery { api.getSimilarMovieList(1, any()) } returns
                    MovieListResponse(1, listOf(fakeMovieDto()), 1, 1)

            repo.loadSimilarMovieList(1)
            repo.loadSimilarMovieList(1)

            coVerify(exactly = 1) { api.getSimilarMovieList(1, any()) }
        }

    @Test
    fun `loadSimilarMovieList falls back to db when offline`() = runTest {
        val repo = createRepo()
        // Pre-populate DB with similar movie data
        database.similarMovieQueries.insertSimilarMovie(
            similarTo = 1L, movieId = 42L, posterPath = null,
            overview = "Overview", releaseDate = null, title = "Movie 42",
            backdropPath = null, voteCount = 100L, voteAverage = 7.0,
            genreIds = "[28]", sortOrder = 0L,
        )
        coEvery { api.getSimilarMovieList(1, any()) } throws IOException()

        val result = repo.loadSimilarMovieList(1)

        assertEquals(1, result.size)
        assertEquals(42, result[0].id)
    }

    @Test
    fun `loadSimilarMovieList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getSimilarMovieList(1, any()) } throws IOException()

        assertFailsWith<IOException> {
            repo.loadSimilarMovieList(1)
        }
    }

    @Test
    fun `fetchMorePopularMovies does not fall back to db when network fails on page 2`() =
        runTest {
            val repo = createRepo()
            coEvery { api.getPopularMovieList(any(), 1) } returns
                    MovieListResponse(1, listOf(fakeMovieDto(1)), 2, 2)
            coEvery { api.getPopularMovieList(any(), 2) } throws IOException()

            repo.fetchMorePopularMovies()

            assertFailsWith<IOException> {
                repo.fetchMorePopularMovies()
            }
        }

    @Test
    fun `fetchMorePopularMovies accumulates results across pages`() = runTest {
        val repo = createRepo()
        coEvery { api.getPopularMovieList(any(), 1) } returns
                MovieListResponse(1, listOf(fakeMovieDto(1)), 2, 2)
        coEvery { api.getPopularMovieList(any(), 2) } returns
                MovieListResponse(2, listOf(fakeMovieDto(2)), 2, 2)

        repo.fetchMorePopularMovies()
        val result = repo.fetchMorePopularMovies()

        assertEquals(2, result.size)
    }

    @Test
    fun `refreshPopularMovieList resets pagination so next fetch starts from page 1`() = runTest {
        val repo = createRepo()
        coEvery { api.getPopularMovieList(any(), any()) } returns
                MovieListResponse(1, listOf(fakeMovieDto()), 1, 1)

        repo.fetchMorePopularMovies()
        repo.fetchMorePopularMovies()
        repo.refreshPopularMovieList()

        repo.fetchMorePopularMovies()

        coVerify(exactly = 2) { api.getPopularMovieList(any(), 1) }
    }
}
