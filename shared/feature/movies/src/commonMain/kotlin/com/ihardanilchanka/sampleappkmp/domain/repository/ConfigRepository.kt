package com.ihardanilchanka.sampleappkmp.domain.repository

import com.ihardanilchanka.sampleappkmp.domain.model.ImageConfig

interface ConfigRepository {
    suspend fun loadConfig(): ImageConfig
}
