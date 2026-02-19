package com.ihardanilchanka.sampleappkmp.di

import com.ihardanilchanka.sampleappkmp.data.MoviesRestInterface
import com.ihardanilchanka.sampleappkmp.data.database.DatabaseDriverFactory
import com.ihardanilchanka.sampleappkmp.data.database.MoviesDatabase
import com.ihardanilchanka.sampleappkmp.data.repository.ConfigRepositoryImpl
import com.ihardanilchanka.sampleappkmp.data.repository.GenreRepositoryImpl
import com.ihardanilchanka.sampleappkmp.data.repository.MovieRepositoryImpl
import com.ihardanilchanka.sampleappkmp.data.repository.ReviewRepositoryImpl
import com.ihardanilchanka.sampleappkmp.domain.navigation.MoviesNavigation
import com.ihardanilchanka.sampleappkmp.domain.usecase.NavigateUpUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.ShowMovieDetailUseCase
import com.ihardanilchanka.sampleappkmp.domain.repository.ConfigRepository
import com.ihardanilchanka.sampleappkmp.domain.repository.GenreRepository
import com.ihardanilchanka.sampleappkmp.domain.repository.MovieRepository
import com.ihardanilchanka.sampleappkmp.domain.repository.ReviewRepository
import com.ihardanilchanka.sampleappkmp.domain.usecase.LoadConfigUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.LoadGenreListUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.LoadReviewListUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.MovieConfigUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.MovieUseCase
import com.ihardanilchanka.sampleappkmp.domain.usecase.SelectedMovieUseCase
import com.ihardanilchanka.sampleappkmp.navigation.AppNavigation
import org.koin.dsl.module

val moviesFeatureModule = module {
    single { get<DatabaseDriverFactory>().createDriver(MoviesDatabase.Schema, "movies.db") }
    single { MoviesDatabase(get()) }

    single { MoviesRestInterface(get()) }

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<GenreRepository> { GenreRepositoryImpl(get(), get()) }
    single<ConfigRepository> { ConfigRepositoryImpl(get(), get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(get(), get()) }

    single<MoviesNavigation> { AppNavigation(get()) }

    factory { MovieUseCase.LoadSimilar(get(), get()) }
    factory { MovieUseCase.LoadPopular(get(), get()) }
    factory { MovieUseCase.RefreshPopular(get(), get()) }
    factory { MovieUseCase.FetchMorePopular(get(), get()) }
    factory { SelectedMovieUseCase.Load(get()) }
    factory { SelectedMovieUseCase.Select(get(), get()) }
    factory { LoadConfigUseCase(get()) }
    factory { LoadGenreListUseCase(get()) }
    factory { LoadReviewListUseCase(get()) }
    factory { MovieConfigUseCase(get(), get()) }
    factory { ShowMovieDetailUseCase(get()) }
    factory { NavigateUpUseCase(get()) }
}
