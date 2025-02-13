package com.yapptek.recompositionlab.data

import kotlinx.coroutines.delay
import javax.inject.Inject

class SubscriptionRepository @Inject constructor() {
    private var isSubscribed = false

    suspend fun subscribeForUpdates(shouldSubscribe: Boolean) {
        // Simulate a network call
        delay(2000)
        isSubscribed = shouldSubscribe
    }

    suspend fun isSubscribed(): Boolean {
        // Simulate a network call
        delay(2000)
        return isSubscribed
    }
}
