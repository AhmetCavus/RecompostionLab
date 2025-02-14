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
import com.yapptek.recompositionlab.ui.viewmodel.SubscriptionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapptek.recompositionlab.data.SubscriptionRepository
import com.yapptek.recompositionlab.domain.SubscriptionUseCase
import com.yapptek.recompositionlab.ui.viewmodel.SubscriptionState

@Composable
fun OptimizedPerformingScreen(
    viewModel: SubscriptionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val subscriptionState by viewModel.subscriptionState.collectAsStateWithLifecycle()
    val loadingState by viewModel.loadingState.collectAsStateWithLifecycle()

    val loadSubscription = remember(viewModel) { viewModel::loadSubscription }
    val updateSubscription = remember(viewModel) { viewModel::updateSubscription }
    var localSubscriptionState = remember { false }

    LaunchedEffect(viewModel) {
        loadSubscription()
    }

    Box(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Optimized Performing Screen", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            SubscriptionCheckbox(
                remoteSubscription = subscriptionState,
                isLoading = loadingState,
                onCheckedChangeLocally = {
                    localSubscriptionState = it
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.lorem_ipsum),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            UpdateDataButton(
                isLoading = loadingState,
                onClick = {
                    updateSubscription(localSubscriptionState)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        LoadingIndicator(
            isVisible = loadingState,
            modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
        )
    }
}

@Composable
private fun SubscriptionCheckbox(
    remoteSubscription: SubscriptionState?,
    isLoading: Boolean,
    onCheckedChangeLocally: (Boolean) -> Unit,
) {
    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
        Text("Subscribe for updates", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))

        var shouldSubscribe by remember { mutableStateOf(false) }
        LaunchedEffect(remoteSubscription) {
            shouldSubscribe = if (remoteSubscription is SubscriptionState.Success) {
                remoteSubscription.isSubscribed
            } else {
                false
            }
        }

        Checkbox(
            enabled = !isLoading,
            checked = shouldSubscribe,
            onCheckedChange = {
                shouldSubscribe = it
                onCheckedChangeLocally(it)
            }
        )
    }
}

@Composable
private fun LoadingIndicator(isVisible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
        visible = isVisible
    ) {
        CircularProgressIndicator(modifier = Modifier.height(64.dp), strokeWidth = 2.dp)
    }
}

@Composable
private fun UpdateDataButton(isLoading: Boolean, onClick: () -> Unit) {
    Button(
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text("Update Data")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewOptimizedPerformingScreen() {
    RecompositionLabTheme {
        PoorlyPerformingScreen(SubscriptionViewModel(SubscriptionUseCase(SubscriptionRepository())))
    }
}