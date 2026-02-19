package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.ImageConfig
import com.ihardanilchanka.sampleappkmp.domain.repository.ConfigRepository

class LoadConfigUseCase(
    private val configRepository: ConfigRepository,
) : SuspendNoArgsUseCase<ImageConfig> {
    override suspend fun invoke() = configRepository.loadConfig()
}
