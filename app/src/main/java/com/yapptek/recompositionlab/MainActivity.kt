package com.yapptek.recompositionlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yapptek.recompositionlab.data.SubscriptionRepository
import com.yapptek.recompositionlab.domain.SubscriptionUseCase
import com.yapptek.recompositionlab.ui.screen.ScreenCoordinator
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import com.yapptek.recompositionlab.ui.viewmodel.ScreenViewModel

class MainActivity : ComponentActivity() {

    val viewModel: ScreenViewModel = ScreenViewModel(SubscriptionUseCase(SubscriptionRepository()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecompositionLabTheme {
                ScreenCoordinator(viewModel)
            }
        }
    }
}
