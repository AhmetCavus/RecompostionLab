package com.yapptek.recompositionlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.yapptek.recompositionlab.ui.screen.ScreenCoordinator
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import com.yapptek.recompositionlab.ui.viewmodel.ScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ScreenViewModel by viewModels()

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
