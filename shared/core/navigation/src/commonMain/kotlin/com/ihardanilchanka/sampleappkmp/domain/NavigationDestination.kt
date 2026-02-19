package com.ihardanilchanka.sampleappkmp.domain

sealed class NavigationDestination {

    data class Route(
        val route: String,
        val launchSingleTop: Boolean = false,
        val popUpTo: String? = null,
        val popUpToInclusive: Boolean = false,
    ) : NavigationDestination()

    data object Up : NavigationDestination()
}
