package com.yapptek.recompositionlab.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubscriptionRepository @Inject constructor() {
    private var isSubscribed = false

    suspend fun subscribeForUpdates(shouldSubscribe: Boolean) {
        withContext(Dispatchers.IO) {
            // Simulate a network call
            delay(2000)
            isSubscribed = shouldSubscribe
        }
    }

    suspend fun isSubscribed(): Boolean {
        return withContext(Dispatchers.IO) {
            // Simulate a network call
            delay(2000)
            isSubscribed
        }
    }
}
