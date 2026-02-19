package com.ihardanilchanka.sampleappkmp.data.model

import com.ihardanilchanka.sampleappkmp.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
) {

    fun toModel() = Genre(id = id, name = name)
}
