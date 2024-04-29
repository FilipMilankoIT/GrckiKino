package com.mozzartbet.grckikino.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.grckikino.R
import com.mozzartbet.grckikino.MainViewModel
import com.mozzartbet.grckikino.ui.components.DrawListItem
import com.mozzartbet.grckikino.ui.components.NavigationBar
import com.mozzartbet.grckikino.ui.navigation.Screen
import com.mozzartbet.grckikino.ui.navigation.navigateToSingleTop

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextDrawsScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val networkConnection by mainViewModel.networkConnection.collectAsStateWithLifecycle()
    val nextDraws by mainViewModel.nextDraws.collectAsStateWithLifecycle()
    val refreshing by mainViewModel.waiting.collectAsStateWithLifecycle()

    Column {
        Column(
            Modifier
                .weight(1f)
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.draw_time),
                    color = MaterialTheme.colors.onBackground
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.remaining_time),
                    color = MaterialTheme.colors.onBackground
                )
            }

            val pullRefreshState = rememberPullRefreshState(
                refreshing = refreshing,
                onRefresh = {
                    mainViewModel.fetchNextDraw()
                })

            LazyColumn(
                Modifier
                    .weight(1f)
                    .pullRefresh(pullRefreshState)
            ) {
                itemsIndexed(nextDraws) { _, item ->
                    key(item.drawID) {
                        DrawListItem(
                            draw = item,
                            onTimer = {
                                mainViewModel.fetchNextDraw()
                            },
                            onClick = {
                                navController.navigateToSingleTop(
                                    "${Screen.DRAW_SCREEN}/${item.drawID}"
                                )
                            }
                        )

                        Spacer(Modifier.size(8.dp))
                    }
                }
            }
        }

        NavigationBar(navController)

        LaunchedEffect(networkConnection) {
            if (networkConnection) {
                mainViewModel.fetchNextDraw()
            }
        }
    }
}

@Preview
@Composable
private fun NextDrawsScreenPreview() {
    NextDrawsScreen(viewModel(), rememberNavController())
}