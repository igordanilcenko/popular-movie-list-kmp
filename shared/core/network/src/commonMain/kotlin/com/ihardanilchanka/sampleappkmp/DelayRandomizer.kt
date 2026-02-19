package com.ihardanilchanka.sampleappkmp

import kotlinx.coroutines.delay

/**
 * Enables network simulation in [simulateNetworkDelay].
 * Set to true in debug application builds; leave false for tests and release.
 */
var networkSimulationEnabled: Boolean = false

/**
 * No-op in tests and release builds.
 * In debug builds (when [networkSimulationEnabled] is true): adds a random 1-4 s delay,
 * and 1-in-4 calls throw to simulate a transient network error.
 */
suspend fun simulateNetworkDelay() {
    if (!networkSimulationEnabled) return
    val delaySeconds = (1..4).random()
    delay(delaySeconds * 1000L)
    if (delaySeconds == 4) {
        error("Some artificial error occurred!")
    }
}
