package com.ihardanilchanka.sampleappkmp.android.feature.movies.presentation

sealed class LoadingState {
    object Ready : LoadingState()
    object Loading : LoadingState()
    data class Error(val error: Throwable) : LoadingState()
}