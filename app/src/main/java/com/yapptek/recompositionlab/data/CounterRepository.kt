package com.yapptek.recompositionlab.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CounterRepository @Inject constructor(private val remoteCountDataSource: RemnoteCountDataSource) {

    suspend fun fetchCount(): Int {
        return withContext(Dispatchers.IO) {
            remoteCountDataSource.fetchCount()
        }
    }

    suspend fun updateCount(newCount: Int) {
        withContext(Dispatchers.IO) {
            remoteCountDataSource.updateCount(newCount)
        }
    }
}
