package com.yapptek.recompositionlab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapptek.recompositionlab.domain.SubscriptionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScreenViewModel(
    private val subscriptionUseCase: SubscriptionUseCase
) : ViewModel() {

    private val poorlyDesignedUiStateEmitter = MutableStateFlow<PoorlyDesignedState>(PoorlyDesignedState.Idle)
    val poorlyDesignedUiState = poorlyDesignedUiStateEmitter.asStateFlow()

    private val subscriptionStateEmitter = MutableStateFlow<SubscriptionState?>(null)
    val subscriptionState = subscriptionStateEmitter.asStateFlow()

    private val loadingStateEmitter = MutableStateFlow<Boolean>(false)
    val loadingState = loadingStateEmitter.asStateFlow()

    fun loadSubscriptionForPoorlyDesign() {
        viewModelScope.launch {
            poorlyDesignedUiStateEmitter.update { PoorlyDesignedState.Loading }
            val isSubscribed = subscriptionUseCase.isSubscribed()
            poorlyDesignedUiStateEmitter.update { PoorlyDesignedState.Success(isSubscribed) }
        }
    }

    fun updateSubscriptionForPoorlyDesign(shouldSubscribe: Boolean) {
        viewModelScope.launch {
            poorlyDesignedUiStateEmitter.update { PoorlyDesignedState.Loading }
            subscriptionUseCase.subscribeForUpdates(shouldSubscribe)
            poorlyDesignedUiStateEmitter.update { PoorlyDesignedState.Success(shouldSubscribe) }
        }
    }

    fun loadSubscription() {
        viewModelScope.launch {
            loadingStateEmitter.update { true }
            val isSubscribed = subscriptionUseCase.isSubscribed()
            subscriptionStateEmitter.update { SubscriptionState.Success(isSubscribed) }
            loadingStateEmitter.update { false }
        }
    }

    fun updateSubscription(shouldSubscribe: Boolean) {
        viewModelScope.launch {
            loadingStateEmitter.update { true }
            subscriptionUseCase.subscribeForUpdates(shouldSubscribe)
            subscriptionStateEmitter.update { SubscriptionState.Success(shouldSubscribe) }
            loadingStateEmitter.update { false }
        }
    }
}

sealed interface PoorlyDesignedState {
    data object Idle : PoorlyDesignedState
    data object Loading : PoorlyDesignedState
    data class Success(val isSubscribed: Boolean) : PoorlyDesignedState
    data object Failed : PoorlyDesignedState

    fun isLoading() = this is Loading
}

sealed interface SubscriptionState {
    data class Success(val isSubscribed: Boolean) : SubscriptionState
    data class Failed(val ex: Throwable) : SubscriptionState
}
