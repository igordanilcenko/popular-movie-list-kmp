package com.ihardanilchanka.sampleappkmp.di

import com.ihardanilchanka.sampleappkmp.data.database.DatabaseDriverFactory
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.HttpClientEngineFactory
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

val platformModule = module {
    single { DatabaseDriverFactory() }
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single<HttpClientEngineFactory<*>> { httpEngineFactory }
}
