package com.ihardanilchanka.sampleappkmp.android.app

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.ihardanilchanka.sampleappkmp.BuildConfig
import com.ihardanilchanka.sampleappkmp.android.core.navigation.ComposeNavigation
import com.ihardanilchanka.sampleappkmp.android.feature.movies.di.moviesPresentationModule
import com.ihardanilchanka.sampleappkmp.di.platformModule
import com.ihardanilchanka.sampleappkmp.di.sharedModule
import com.ihardanilchanka.sampleappkmp.networkSimulationEnabled
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MovieApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()

        networkSimulationEnabled = BuildConfig.DEBUG

        startKoin {
            androidContext(this@MovieApp)
            modules(
                sharedModule,
                platformModule,
                moviesPresentationModule,
                androidAppModule,
            )
        }
    }

    override fun newImageLoader(context: android.content.Context) = ImageLoader.Builder(context)
        .crossfade(true)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, 0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()
}

private val androidAppModule = module {
    factory { ComposeNavigation(get()) }
}
