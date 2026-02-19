package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.ApiConfig
import com.ihardanilchanka.sampleappkmp.data.MoviesRestInterface
import com.ihardanilchanka.sampleappkmp.data.model.ImageConfigDto
import com.ihardanilchanka.sampleappkmp.domain.model.ImageConfig
import com.ihardanilchanka.sampleappkmp.domain.repository.ConfigRepository
import com.russhwolf.settings.Settings
import kotlinx.io.IOException
import kotlinx.serialization.json.Json

class ConfigRepositoryImpl(
    private val moviesRestInterface: MoviesRestInterface,
    private val json: Json,
    private val settings: Settings,
) : ConfigRepository {

    private var imageConfigCache: ImageConfig? = null

    override suspend fun loadConfig() = imageConfigCache
        ?: try {
            moviesRestInterface.getConfiguration(ApiConfig.API_KEY).imageConfigDto
                .also { saveImageConfig(it) }
        } catch (e: IOException) {
            getImageConfig() ?: throw e
        }
            .toModel()
            .also { imageConfigCache = it }

    private fun saveImageConfig(config: ImageConfigDto) {
        settings.putString(KEY_CONFIG, json.encodeToString(config))
    }

    private fun getImageConfig(): ImageConfigDto? {
        return settings.getStringOrNull(KEY_CONFIG)?.let { stored ->
            json.decodeFromString<ImageConfigDto>(stored)
        }
    }

    companion object {
        private const val KEY_CONFIG = "KEY_CONFIG"
    }
}
