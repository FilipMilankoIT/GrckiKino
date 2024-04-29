package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.grckikino.R
import com.mozzartbet.grckikino.ui.navigation.NavItem
import com.mozzartbet.grckikino.ui.navigation.Screen

@Composable
fun NavigationBar(navController: NavController) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
        Alignment.Center
    ) {

        BottomNavigation(
            modifier = Modifier.matchParentSize(),
            backgroundColor = MaterialTheme.colors.primaryVariant,
            elevation = 3.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val navItems = listOf(
                NavItem(R.drawable.list_icon, R.string.draws, Screen.NEXT_DRAWS_SCREEN.route),
                NavItem(R.drawable.play_icon, R.string.live_draws, Screen.LIVE_DRAWS_SCREEN.route),
                NavItem(R.drawable.result_icon, R.string.draws_results, Screen.DRAW_RESULTS_SCREEN.route)
            )

            navItems.forEach { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = item.icon),
                            "",
                            Modifier.size(35.dp)
                        )
                    },
                    modifier = Modifier.fillMaxHeight(),
                    label = { Text(stringResource(item.label)) },
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
                    selected = currentRoute == item.screenRoute,
                    onClick = {
                        navController.navigate(item.screenRoute) {
                            popUpTo(Screen.NEXT_DRAWS_SCREEN.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NavigationBarPreview() {
    NavigationBar(rememberNavController())
}