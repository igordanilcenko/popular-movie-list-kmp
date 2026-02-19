package com.ihardanilchanka.sampleappkmp.data.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.model.ReviewListResponse
import com.ihardanilchanka.sampleappkmp.data.network.MoviesApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class ReviewRepositoryTest {

    private val api = mockk<MoviesApi>()
    private lateinit var database: MoviesDatabase

    @Before
    fun setUp() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MoviesDatabase.Schema.create(driver)
        database = MoviesDatabase(driver)
    }

    private fun createRepo() = ReviewRepositoryImpl(api, database)

    @Test
    fun `loadReviewList uses memory cache and skips network on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getMovieReviews(1, any()) } returns
                ReviewListResponse(1, listOf(fakeReviewDto()))

        repo.loadReviewList(1)
        repo.loadReviewList(1)

        coVerify(exactly = 1) { api.getMovieReviews(1, any()) }
    }

    @Test
    fun `loadReviewList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getMovieReviews(1, any()) } throws IOException()

        assertFailsWith<IOException> {
            repo.loadReviewList(1)
        }
    }
}
