package com.ihardanilchanka.sampleappkmp.di

import io.ktor.client.engine.HttpClientEngineFactory

expect val httpEngineFactory: HttpClientEngineFactory<*>
