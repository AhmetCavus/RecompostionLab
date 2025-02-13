package com.yapptek.recompositionlab.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapptek.recompositionlab.R
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import com.yapptek.recompositionlab.ui.viewmodel.ScreenViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapptek.recompositionlab.data.SubscriptionRepository
import com.yapptek.recompositionlab.domain.SubscriptionUseCase
import com.yapptek.recompositionlab.ui.viewmodel.PoorlyDesignedState

@Composable
fun PoorlyPerformingScreen(
    viewModel: ScreenViewModel = hiltViewModel(),
    modifier : Modifier = Modifier,
) {
    val uiState by viewModel.poorlyDesignedUiState.collectAsStateWithLifecycle()
    val updateSubsription = viewModel::updateSubscriptionForPoorlyDesign
    val loadSubscription = viewModel::loadSubscriptionForPoorlyDesign

    LaunchedEffect(Unit) {
        loadSubscription()
    }

    val isLoading = uiState.isLoading()

    Box(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Poorly Performing Screen", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            var isSubscribedLocal by remember { mutableStateOf(false) }

            LaunchedEffect(uiState) {
                isSubscribedLocal = if (uiState is PoorlyDesignedState.Success) {
                    (uiState as PoorlyDesignedState.Success).isSubscribed
                } else {
                    false
                }
            }

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Subscribe for updates", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.weight(1f))

                Checkbox(
                    enabled = !isLoading,
                    checked = isSubscribedLocal,
                    onCheckedChange = { isSubscribedLocal = it }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.lorem_ipsum),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    updateSubsription(isSubscribedLocal)
                }) {
                Text("Update Data")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
            visible = isLoading
        ) {
            CircularProgressIndicator(
                modifier = Modifier.height(64.dp),
                strokeWidth = 2.dp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPoorlyPerformingScreen() {
    RecompositionLabTheme {
        PoorlyPerformingScreen(ScreenViewModel(SubscriptionUseCase(SubscriptionRepository())))
    }
}
