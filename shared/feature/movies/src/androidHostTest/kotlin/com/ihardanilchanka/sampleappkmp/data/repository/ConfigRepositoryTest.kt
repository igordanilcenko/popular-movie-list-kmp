package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.data.model.ConfigurationResponse
import com.ihardanilchanka.sampleappkmp.data.network.MoviesApi
import com.russhwolf.settings.Settings
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ConfigRepositoryTest {

    private val api = mockk<MoviesApi>()
    private val settings = mockk<Settings>(relaxed = true)
    private val json = Json

    private fun createRepo() = ConfigRepositoryImpl(api, json, settings)

    @Test
    fun `loadConfig uses memory cache and skips network and settings on second call`() = runTest {
        val repo = createRepo()
        coEvery { api.getConfiguration(any()) } returns ConfigurationResponse(fakeImageConfigDto())

        repo.loadConfig()
        repo.loadConfig()

        coVerify(exactly = 1) { api.getConfiguration(any()) }
        verify(exactly = 0) { settings.getStringOrNull(any()) }
    }

    @Test
    fun `loadConfig falls back to settings when offline`() = runTest {
        val repo = createRepo()
        val config = fakeImageConfigDto()
        val configJson = json.encodeToString(config)
        coEvery { api.getConfiguration(any()) } throws IOException()
        every { settings.getStringOrNull(any()) } returns configJson

        val result = repo.loadConfig()

        assertEquals(config.baseUrl, result.baseUrl)
    }

    @Test
    fun `loadConfig rethrows exception when offline and settings is empty`() = runTest {
        val repo = createRepo()
        coEvery { api.getConfiguration(any()) } throws IOException()
        every { settings.getStringOrNull(any()) } returns null

        assertFailsWith<IOException> {
            repo.loadConfig()
        }
    }
}
