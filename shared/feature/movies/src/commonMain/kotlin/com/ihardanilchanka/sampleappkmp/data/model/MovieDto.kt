package com.ihardanilchanka.sampleappkmp.data.model

import com.ihardanilchanka.sampleappkmp.domain.model.RawMovie
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("overview") val overview: String,
    @SerialName("release_date") val releaseDateStr: String? = null,
    @SerialName("title") val title: String,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("genre_ids") val genreIds: List<Int>,
) {

    val releaseDate: LocalDate?
        get() = releaseDateStr?.takeIf { it.isNotBlank() }?.let {
            runCatching { LocalDate.parse(it) }.getOrNull()
        }

    fun toRawMovie() = RawMovie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
    )
}
