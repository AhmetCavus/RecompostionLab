package com.yapptek.recompositionlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yapptek.recompositionlab.ui.screen.ScreenCoordinator
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecompositionLabTheme {
                ScreenCoordinator()
            }
        }
    }
}
