package com.ihardanilchanka.sampleappkmp.di

import org.koin.dsl.module

val sharedModule = module {
    includes(
        networkCoreModule,
        navigationCoreModule,
        moviesFeatureModule,
    )
}
