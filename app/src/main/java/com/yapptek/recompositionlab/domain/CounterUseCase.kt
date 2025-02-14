package com.yapptek.recompositionlab.domain

import com.yapptek.recompositionlab.data.CounterRepository
import javax.inject.Inject

class CounterUseCase @Inject constructor(
    private val counterRepository: CounterRepository
){
    suspend fun fetchCount() = counterRepository.fetchCount()
    suspend fun updateCount(newCount: Int) = counterRepository.updateCount(newCount)
}