package com.ihardanilchanka.sampleappkmp.domain.repository

import com.ihardanilchanka.sampleappkmp.domain.model.Genre

interface GenreRepository {
    suspend fun loadGenreList(): List<Genre>
}
