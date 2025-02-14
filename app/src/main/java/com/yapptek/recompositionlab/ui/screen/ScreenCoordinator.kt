package com.yapptek.recompositionlab.ui.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yapptek.recompositionlab.ui.theme.RecompositionLabTheme
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCoordinator() {
    val navController = rememberNavController()
    RecompositionLabTheme {
        Scaffold(
            modifier = Modifier.safeContentPadding(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Recomposition Lab") },
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.PoorlyPerformingScreen,
            ) {
                composable<Screen.PoorlyPerformingScreen> {
                    PoorlyPerformingScreen(modifier = Modifier.padding(innerPadding),)
                }
                composable<Screen.OptimisedPerformingScreen> {
                    OptimizedPerformingScreen(modifier = Modifier.padding(innerPadding))
                }
                composable<Screen.BestPerformingScreen> {
                    BestPerformingScreen(modifier = Modifier.padding(innerPadding))
                }
                composable<Screen.BestPerformingScreen> {
                    BestPerformingScreen(modifier = Modifier.padding(innerPadding))
                }
                composable<Screen.NonRestartableListItemScreen> {
                    NonRestartableListItemScreen(
                        Modifier.padding(innerPadding),
                    )
                }
                composable<Screen.MovableListItemScreen> {
                    val items = remember { (0..1000).map { ListItem(it, "Item $it") } }
                    MoveableListItemScreen(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
                composable<Screen.CounterScreen> {
                    CounterScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

sealed interface Screen {

    @Serializable
    data object PoorlyPerformingScreen : Screen

    @Serializable
    data object OptimisedPerformingScreen : Screen

    @Serializable
    data object BestPerformingScreen : Screen

    @Serializable
    data object NonRestartableListItemScreen : Screen

    @Serializable
    data object MovableListItemScreen : Screen

    @Serializable
    data object CounterScreen : Screen
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 0,
                onClick = {
                    navController.navigate(Screen.PoorlyPerformingScreen)
                    selectedTabIndex = 0
                }
            ) {
                Text("1")
            }
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 1,
                onClick = {
                    navController.navigate(Screen.OptimisedPerformingScreen)
                    selectedTabIndex = 1
                }
            ) {
                Text("2")
            }
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 2,
                onClick = {
                    navController.navigate(Screen.BestPerformingScreen)
                    selectedTabIndex = 2}
            ) {
                Text("3")
            }
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 3,
                onClick = {
                    navController.navigate(Screen.NonRestartableListItemScreen)
                    selectedTabIndex = 3}
            ) {
                Text("4")
            }
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 4,
                onClick = {
                    navController.navigate(Screen.MovableListItemScreen)
                    selectedTabIndex = 4}
            ) {
                Text("5")
            }
            Tab(
                modifier = Modifier.height(64.dp),
                selected = selectedTabIndex == 5,
                onClick = {
                    navController.navigate(Screen.CounterScreen)
                    selectedTabIndex = 5}
            ) {
                Text("6")
            }
        }
    }
}

@Preview
@Composable
fun ScreenCoordinatorPreview() {
    RecompositionLabTheme {
        ScreenCoordinator()
    }
}