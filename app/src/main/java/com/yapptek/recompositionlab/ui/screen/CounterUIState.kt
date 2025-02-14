package com.yapptek.recompositionlab.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.setValue

@Stable
class CounterUIState(private val count: Int = 0) {
    companion object {
        val Saver = object : Saver<CounterUIState, Int> {
            override fun restore(value: Int): CounterUIState {
                return CounterUIState(count = value)
            }

            override fun SaverScope.save(state: CounterUIState): Int {
                return state.count
            }
        }
    }

    var countState by mutableIntStateOf(count)
        private set

    fun increment() {
        countState += 1
    }

    fun reset() {
        countState = 0
    }
}

@Composable
fun rememberCounterUIState(count: Int = 0): CounterUIState {
    return rememberSaveable(saver = CounterUIState.Saver) {
        CounterUIState(count)
    }
}