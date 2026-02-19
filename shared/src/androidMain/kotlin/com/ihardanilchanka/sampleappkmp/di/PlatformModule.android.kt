package com.ihardanilchanka.sampleappkmp.di

import com.ihardanilchanka.sampleappkmp.data.database.DatabaseDriverFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.HttpClientEngineFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<Settings> {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences("SampleApp", android.content.Context.MODE_PRIVATE)
        )
    }
    single<HttpClientEngineFactory<*>> { httpEngineFactory }
}
