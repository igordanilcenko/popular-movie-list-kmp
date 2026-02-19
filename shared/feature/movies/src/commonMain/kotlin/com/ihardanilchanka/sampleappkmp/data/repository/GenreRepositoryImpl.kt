package com.ihardanilchanka.sampleappkmp.data.repository

import com.ihardanilchanka.sampleappkmp.ApiConfig
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.network.MoviesApi
import com.ihardanilchanka.sampleappkmp.domain.model.Genre
import com.ihardanilchanka.sampleappkmp.domain.repository.GenreRepository
import kotlinx.io.IOException

class GenreRepositoryImpl(
    private val moviesApi: MoviesApi,
    private val database: MoviesDatabase,
) : GenreRepository {

    private var genresCache: List<Genre>? = null

    override suspend fun loadGenreList() = genresCache ?: try {
        val dtos = moviesApi.getGenreList(ApiConfig.API_KEY).genres
        database.genreQueries.deleteAll()
        dtos.forEach { dto ->
            database.genreQueries.insertGenre(dto.id.toLong(), dto.name)
        }
        dtos.map { it.toModel() }
    } catch (e: IOException) {
        database.genreQueries.getAll().executeAsList()
            .takeIf { it.isNotEmpty() }
            ?.map { Genre(id = it.id.toInt(), name = it.name) }
            ?: throw e
    }.also { genresCache = it }
}
