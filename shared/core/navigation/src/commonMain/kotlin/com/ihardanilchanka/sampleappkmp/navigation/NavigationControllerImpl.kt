package com.ihardanilchanka.sampleappkmp.navigation

import com.ihardanilchanka.sampleappkmp.domain.NavigationController
import com.ihardanilchanka.sampleappkmp.domain.NavigationDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NavigationControllerImpl : NavigationController {

    private val _navigation =
        MutableSharedFlow<NavigationDestination>(replay = 0, extraBufferCapacity = 1)

    override fun navigate(destination: NavigationDestination) {
        _navigation.tryEmit(destination)
    }

    override fun observeNavigation() = _navigation.asSharedFlow()
}
