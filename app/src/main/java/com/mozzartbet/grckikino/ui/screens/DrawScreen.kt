package com.mozzartbet.grckikino.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.grckikino.R
import com.mozzartbet.grckikino.MainViewModel
import com.mozzartbet.grckikino.ui.components.NumberBoard
import com.mozzartbet.grckikino.ui.components.NumberPickerDialog
import com.mozzartbet.grckikino.ui.components.Timer
import java.text.SimpleDateFormat
import java.util.Locale

private const val NUMBERS = 80
private const val MAX_SELECTED_NUMBERS = 15

@Composable
fun DrawScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    drawID: Int
) {
    val networkConnection by mainViewModel.networkConnection.collectAsStateWithLifecycle()
    val draw by mainViewModel.selectedDraw.collectAsStateWithLifecycle()

    var selectedNumbers by rememberSaveable { mutableStateOf(Array(NUMBERS) { false }) }
    val numberCount = selectedNumbers.count { it }

    var showSelectNumberDialog by rememberSaveable { mutableStateOf(false) }

    val betOdds = listOf(
        1 to 3.75,
        2 to 14,
        3 to 65,
        4 to 275,
        5 to 1350,
        6 to 6500,
        7 to 25000,
        8 to 125000
    )

    val context = LocalContext.current
    val maxSelectedNumbersMessage =
        stringResource(R.string.max_selected_numbers_message, MAX_SELECTED_NUMBERS)
    val timesUpMessage =
        stringResource(R.string.times_up_message, MAX_SELECTED_NUMBERS)

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10f))
                .clickable {
                    navController.popBackStack()
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.back_icon),
                "",
                Modifier.size(25.dp),
                MaterialTheme.colors.primary
            )

            Text(
                text = stringResource(R.string.back),
                color = MaterialTheme.colors.primary
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            draw?.let {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val formatter = SimpleDateFormat("hh:mm", Locale.ENGLISH)
                    val formattedTime = formatter.format(it.drawTime)

                    Text(
                        text =  "${stringResource(R.string.draw_time)}: $formattedTime",
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(Modifier.weight(1f))

                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colors.onBackground
                    ) {
                        Timer(it.drawTime) {
                            Toast.makeText(context, timesUpMessage, Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    }
                }

                Text(
                    text =  "${stringResource(R.string.draw)}: ${draw?.drawID ?: ""}",
                    color = MaterialTheme.colors.onBackground
                )

                Text(
                    text =  "${stringResource(R.string.odds)}:",
                    color = MaterialTheme.colors.onBackground
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                betOdds.forEach {
                    Text(
                        text = it.first.toString(),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colors.onSurface)
            )

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                betOdds.forEach {
                    Text(
                        text = it.second.toString(),
                        Modifier.weight(1f),
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showSelectNumberDialog = true },
                modifier = Modifier.height(40.dp)
            ) {
                Text(text = stringResource(R.string.random_selection))
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = stringResource(R.string.numbers, numberCount),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(10f / (NUMBERS.toFloat() / 10f))
                .padding(horizontal = 8.dp)
        ) {
            NumberBoard(selectedNumbers) {
                val isSelected = selectedNumbers[it]
                if (!isSelected && numberCount >= MAX_SELECTED_NUMBERS) {
                    Toast.makeText(context, maxSelectedNumbersMessage, Toast.LENGTH_SHORT).show()
                } else {
                    selectedNumbers[it] = !isSelected
                    selectedNumbers = selectedNumbers.clone()
                }
            }
        }

        Box(Modifier.padding(8.dp)) {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.bet))
            }
        }

        if (showSelectNumberDialog) {
            NumberPickerDialog(
                minNumber = 1.coerceAtLeast(numberCount),
                maxNumber = MAX_SELECTED_NUMBERS,
                onPositive = {
                    selectedNumbers = getRandomNumbers(it)
                    showSelectNumberDialog = false
                },
                onNegative = { showSelectNumberDialog = false }
            )
        }

        LaunchedEffect(networkConnection) {
            if (networkConnection) {
                mainViewModel.fetchSelectedDraw(drawID)
            }
        }
    }
}

private fun getRandomNumbers(numberCount: Int): Array<Boolean> {
    val indexes = (0 until NUMBERS).toMutableList()
    indexes.shuffle()
    val array = Array(NUMBERS) { false }
    for(i in 0 until numberCount.coerceAtMost(NUMBERS)) {
        array[indexes[i]] = true
    }
    return array
}

@Preview
@Composable
private fun DrawScreenPreview() {
    DrawScreen(viewModel(), rememberNavController(), 1085309)
}