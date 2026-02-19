package com.ihardanilchanka.sampleappkmp.navigation

import com.ihardanilchanka.sampleappkmp.domain.NavigationController
import com.ihardanilchanka.sampleappkmp.domain.NavigationDestination
import com.ihardanilchanka.sampleappkmp.domain.navigation.MoviesNavigation

class AppNavigation(
    private val navigationController: NavigationController,
) : MoviesNavigation {
    override fun goBack() {
        navigationController.navigate(NavigationDestination.Up)
    }

    override fun goToMovieDetail() {
        navigationController.navigate(NavigationDestination.Route(route = AppNavigationRoute.movieDetail))
    }
}
