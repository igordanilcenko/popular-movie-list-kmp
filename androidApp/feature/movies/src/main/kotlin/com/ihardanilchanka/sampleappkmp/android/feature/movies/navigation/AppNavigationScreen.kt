package com.ihardanilchanka.sampleappkmp.android.feature.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.moviedetail.MovieDetailScreen
import com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation.movielist.MovieListScreen
import com.ihardanilchanka.sampleappkmp.navigation.AppNavigationRoute

@Composable
fun AppNavigationScreen(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = AppNavigationRoute.popularMovieList) {
        composable(AppNavigationRoute.popularMovieList) { MovieListScreen() }
        composable(AppNavigationRoute.movieDetail) { MovieDetailScreen() }
    }
}
