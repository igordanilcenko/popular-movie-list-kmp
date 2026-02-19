package com.ihardanilchanka.sampleappkmp.android.feature.movies.di

import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.MovieDetailViewModel
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist.MovieListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moviesPresentationModule = module {
    viewModel { MovieListViewModel(get(), get(), get(), get()) }
    viewModel { MovieDetailViewModel(get(), get(), get(), get(), get()) }
}
