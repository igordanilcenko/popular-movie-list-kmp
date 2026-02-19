package com.ihardanilchanka.sampleappkmp.data.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.model.GenreListResponse
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

class GenreRepositoryTest {

    private val api = mockk<MoviesApi>()
    private lateinit var database: MoviesDatabase

    @Before
    fun setUp() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        MoviesDatabase.Schema.create(driver)
        database = MoviesDatabase(driver)
    }

    private fun createRepo() = GenreRepositoryImpl(api, database)

    @Test
    fun `loadGenreList uses memory cache and skips network on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getGenreList(any()) } returns GenreListResponse(listOf(fakeGenreDto()))

        repo.loadGenreList()
        repo.loadGenreList()

        coVerify(exactly = 1) { api.getGenreList(any()) }
    }

    @Test
    fun `loadGenreList falls back to db when offline`() = runTest {
        val repo = createRepo()
        // Pre-populate DB with genre data
        database.genreQueries.insertGenre(28L, "Genre 28")
        coEvery { api.getGenreList(any()) } throws IOException()

        val result = repo.loadGenreList()

        assertEquals(1, result.size)
        assertEquals(28, result[0].id)
    }

    @Test
    fun `loadGenreList rethrows exception when offline and db is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getGenreList(any()) } throws IOException()

        assertFailsWith<IOException> {
            repo.loadGenreList()
        }
    }
}
