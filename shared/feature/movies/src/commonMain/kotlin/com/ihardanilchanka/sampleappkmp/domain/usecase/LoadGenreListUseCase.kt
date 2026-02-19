package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.model.Genre
import com.ihardanilchanka.sampleappkmp.domain.repository.GenreRepository

class LoadGenreListUseCase(
    private val genreRepository: GenreRepository,
) : SuspendNoArgsUseCase<List<Genre>> {
    override suspend fun invoke() = genreRepository.loadGenreList()
}
