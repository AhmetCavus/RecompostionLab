package com.yapptek.recompositionlab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapptek.recompositionlab.domain.CounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val counterUseCase: CounterUseCase,
) : ViewModel() {

    private val remoteCounterStateEmitter = MutableStateFlow<Int>(0)
    val remoteCounterState = remoteCounterStateEmitter.asStateFlow()

    private val isLoadingEmitter = MutableStateFlow<Boolean>(false)
    val isLoading = isLoadingEmitter.asStateFlow()

    fun fetchCounterState() {
        isLoadingEmitter.update { true }
        viewModelScope.launch {
            val remoteCount = counterUseCase.fetchCount()
            remoteCounterStateEmitter.update { remoteCount }
            isLoadingEmitter.update { false }
        }
    }

    fun updateCount(newCount: Int) {
        isLoadingEmitter.update { true }
        viewModelScope.launch {
            counterUseCase.updateCount(newCount)
            isLoadingEmitter.update { false }
        }
    }
}
