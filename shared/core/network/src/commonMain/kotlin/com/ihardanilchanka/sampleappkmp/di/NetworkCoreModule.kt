package com.ihardanilchanka.sampleappkmp.di

import com.ihardanilchanka.sampleappkmp.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkCoreModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    single {
        HttpClient(get<HttpClientEngineFactory<*>>()) {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                url(ApiConfig.ENDPOINT)
            }
        }
    }
}
