package com.ihardanilchanka.sampleappkmp.domain

import kotlinx.coroutines.flow.Flow

interface NavigationController {
    fun navigate(destination: NavigationDestination)
    fun observeNavigation(): Flow<NavigationDestination>
}
