package com.ihardanilchanka.sampleappkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images") val imageConfigDto: ImageConfigDto,
)
