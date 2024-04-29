package com.mozzartbet.grckikino

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.grckikino.ui.navigation.Screen
import com.mozzartbet.grckikino.ui.screens.NextDrawsScreen
import com.mozzartbet.grckikino.ui.components.LoaderOverlay
import com.mozzartbet.grckikino.ui.components.NoNetworkOverlay
import com.mozzartbet.grckikino.ui.screens.DrawResultsScreen
import com.mozzartbet.grckikino.ui.screens.DrawScreen
import com.mozzartbet.grckikino.ui.screens.LiveDrawsScreen
import com.mozzartbet.grckikino.ui.theme.MyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            MyTheme {
                NavHost(
                    navController,
                    Screen.NEXT_DRAWS_SCREEN.route,
                    Modifier.imePadding()
                ) {
                    composable(Screen.NEXT_DRAWS_SCREEN.route) {
                        NextDrawsScreen(mainViewModel, navController)
                    }

                    composable(Screen.LIVE_DRAWS_SCREEN.route) {
                        LiveDrawsScreen(mainViewModel, navController)
                    }

                    composable(Screen.DRAW_RESULTS_SCREEN.route) {
                        DrawResultsScreen(mainViewModel, navController)
                    }

                    composable("${Screen.DRAW_SCREEN.route}/{${Screen.DRAW_SCREEN.argumentKey}}") {
                        DrawScreen(mainViewModel, navController,
                            it.arguments?.getString(Screen.DRAW_SCREEN.argumentKey)?.toIntOrNull() ?: -1)
                    }
                }

                if (!mainViewModel.networkConnection.collectAsStateWithLifecycle().value) {
                    NoNetworkOverlay()
                }

                if (mainViewModel.waiting.collectAsStateWithLifecycle().value) {
                    LoaderOverlay()
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.message.collect {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}