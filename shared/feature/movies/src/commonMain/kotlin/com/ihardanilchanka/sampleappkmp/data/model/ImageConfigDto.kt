package com.ihardanilchanka.sampleappkmp.data.model

import com.ihardanilchanka.sampleappkmp.domain.model.ImageConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageConfigDto(
    @SerialName("base_url") val baseUrl: String,
    @SerialName("secure_base_url") val secureBaseUrl: String,
) {

    fun toModel() = ImageConfig(baseUrl = baseUrl, secureBaseUrl = secureBaseUrl)
}
