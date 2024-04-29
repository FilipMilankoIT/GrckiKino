package com.mozzartbet.grckikino.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.mozzartbet.grckikino.ui.components.DrawResultListItem
import com.mozzartbet.grckikino.ui.components.MyDatePickerDialog
import com.mozzartbet.grckikino.ui.components.NavigationBar
import com.mozzartbet.grckikino.utils.TimeUtils
import com.mozzartbet.grckikino.utils.TimeUtils.toFormattedDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawResultsScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val networkConnection by mainViewModel.networkConnection.collectAsStateWithLifecycle()
    val drawResults by mainViewModel.drawResults.collectAsStateWithLifecycle()
    val refreshing by mainViewModel.waiting.collectAsStateWithLifecycle()

    var selectedDate by rememberSaveable { mutableStateOf(TimeUtils.getTodayDate()) }
    var selectedDateType by rememberSaveable { mutableStateOf(SelectedDateType.TODAY) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    Column {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        selectedDateType = SelectedDateType.TODAY
                        selectedDate = TimeUtils.getTodayDate()
                    },
                    modifier = Modifier.height(35.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = if (selectedDateType == SelectedDateType.TODAY) 1f else 0.5f
                        )
                    )
                ) {
                    Text(text = stringResource(R.string.today))
                }

                Spacer(Modifier.size(16.dp))

                Button(
                    onClick = {
                        selectedDateType = SelectedDateType.YESTERDAY
                        selectedDate = TimeUtils.getYesterdayDate()
                    },
                    modifier = Modifier.height(35.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = if (selectedDateType == SelectedDateType.YESTERDAY) 1f else 0.5f
                        )
                    )
                ) {
                    Text(text = stringResource(R.string.yesterday))
                }

                Spacer(Modifier.size(16.dp))

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.height(35.dp),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = if (selectedDateType == SelectedDateType.CUSTOM) 1f else 0.5f
                        )
                    )
                ) {
                    Text(text = stringResource(R.string.select_date))
                }
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
                itemsIndexed(drawResults) { _, item ->
                    key(item.drawID) {
                        DrawResultListItem(item)

                        Spacer(Modifier.size(8.dp))
                    }
                }
            }
        }

        NavigationBar(navController)

        if (showDatePicker) {
            MyDatePickerDialog(
                onPositive = { date ->
                    date?.let {
                        selectedDate = it.toFormattedDateTime(TimeUtils.API_DATE_FORMAT)
                        selectedDateType = SelectedDateType.CUSTOM
                    }
                    showDatePicker = false
                },
                onNegative = {
                    showDatePicker = false
                }
            )
        }

        LaunchedEffect(networkConnection, selectedDate) {
            if (networkConnection) {
                mainViewModel.fetchDrawResults(selectedDate, selectedDate)
            }
        }
    }
}

private enum class SelectedDateType {
    TODAY, YESTERDAY, CUSTOM
}

@Preview
@Composable
private fun DrawResultsScreenPreview() {
    DrawResultsScreen(viewModel(), rememberNavController())
}