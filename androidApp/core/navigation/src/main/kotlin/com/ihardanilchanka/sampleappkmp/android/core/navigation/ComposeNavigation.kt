package com.ihardanilchanka.sampleappkmp.android.core.navigation

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.ihardanilchanka.sampleappkmp.domain.NavigationController
import com.ihardanilchanka.sampleappkmp.domain.NavigationDestination
import kotlinx.coroutines.launch

class ComposeNavigation(
    private val navigationController: NavigationController,
) {

    private lateinit var androidNavController: NavHostController

    fun init(activity: ComponentActivity, navHostController: NavHostController) {
        androidNavController = navHostController

        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.CREATED) {
                navigationController.observeNavigation().collect { destination ->
                    when (destination) {
                        is NavigationDestination.Route -> {
                            androidNavController.navigate(destination.route) {
                                launchSingleTop = destination.launchSingleTop
                                destination.popUpTo?.let {
                                    popUpTo(it) { inclusive = destination.popUpToInclusive }
                                }
                            }
                        }
                        is NavigationDestination.Up -> {
                            androidNavController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}
