package com.yapptek.recompositionlab.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import com.yapptek.recompositionlab.ui.viewmodel.CounterViewModel

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    counterViewModel: CounterViewModel = hiltViewModel()
) {
    val remoteCounterState = counterViewModel.remoteCounterState.collectAsStateWithLifecycle()
    val isLoading = counterViewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(counterViewModel) {
        counterViewModel.fetchCounterState()
    }

    CounterScreen(
        onUpdateCount = counterViewModel::updateCount,
        remoteCounterState = remoteCounterState,
        isLoadingState = isLoading,
        modifier = modifier,
    )
}

@Composable
private fun CounterScreen(
    onUpdateCount: (Int) -> Unit,
    remoteCounterState: State<Int>,
    isLoadingState: State<Boolean>,
    modifier: Modifier = Modifier
) {
    // Sync local counter with the remote one
    val counterUiState = remember(remoteCounterState.value) {
        CounterUIState(remoteCounterState.value)
    }

    Box(modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Header()
            Spacer(modifier = Modifier.height(32.dp))
            Counter(
                isLoadingState = isLoadingState,
                counterUiState = counterUiState,
            )
            Spacer(modifier = Modifier.weight(1f))
            UpdateButton(
                isLoadingState = isLoadingState,
                counterUiState = counterUiState,
                onUpdateCount = onUpdateCount,
            )
        }
        LoadingIndicator(
            isVisibleState = isLoadingState,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
@NonRestartableComposable
private fun Header(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Click the count button for a local increment and than sync for persisting the current count value"
    )
}

@Composable
private fun Counter(
    isLoadingState: State<Boolean>,
    counterUiState: CounterUIState,
    modifier: Modifier = Modifier,
) {
    val isLoading by isLoadingState
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        val counterText = if (isLoading) {
            "---"
        } else {
            "Count at: ${counterUiState.countState}"
        }
        Text(counterText, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoadingState.value,
            onClick = {
                counterUiState.increment()
            }
        ) {
            Text("Increment counter", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(4.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoadingState.value,
            onClick = {
                counterUiState.reset()
            }
        ) {
            Text("Reset counter", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun UpdateButton(
    isLoadingState: State<Boolean>,
    modifier: Modifier = Modifier,
    counterUiState: CounterUIState,
    onUpdateCount: (Int) -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        enabled = !isLoadingState.value,
        onClick = {
        onUpdateCount(counterUiState.countState)
    }) {
        Text("Sync")
    }
}

@Composable
private fun LoadingIndicator(isVisibleState: State<Boolean>, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
        visible = isVisibleState.value
    ) {
        CircularProgressIndicator(modifier = Modifier.height(64.dp), strokeWidth = 2.dp)
    }
}

@Preview
@Composable
private fun PreviewCounter() {
    RecompositionLabTheme {
        CounterScreen()
    }
}
