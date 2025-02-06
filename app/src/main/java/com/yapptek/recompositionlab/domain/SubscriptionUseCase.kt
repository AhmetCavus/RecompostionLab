package com.yapptek.recompositionlab.domain

import com.yapptek.recompositionlab.data.SubscriptionRepository

class SubscriptionUseCase(private val subscriptionRepository: SubscriptionRepository) {

    suspend fun subscribeForUpdates(shouldSubscribe: Boolean) {
        subscriptionRepository.subscribeForUpdates(shouldSubscribe)
    }

    suspend fun isSubscribed(): Boolean {
        return subscriptionRepository.isSubscribed()
    }
}