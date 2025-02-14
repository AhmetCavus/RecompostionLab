package com.yapptek.recompositionlab.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemnoteCountDataSource @Inject constructor() {
    private var remoteCount = 0

    suspend fun fetchCount(): Int {
        delay(1000)
        return remoteCount
    }

    suspend fun updateCount(newCount: Int) {
        delay(1000)
        remoteCount = newCount
    }
}