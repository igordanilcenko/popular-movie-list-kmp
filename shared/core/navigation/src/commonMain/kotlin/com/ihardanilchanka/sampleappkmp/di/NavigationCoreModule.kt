package com.ihardanilchanka.sampleappkmp.di

import com.ihardanilchanka.sampleappkmp.domain.NavigationController
import com.ihardanilchanka.sampleappkmp.navigation.NavigationControllerImpl
import org.koin.dsl.module

val navigationCoreModule = module {
    single<NavigationController> { NavigationControllerImpl() }
}
