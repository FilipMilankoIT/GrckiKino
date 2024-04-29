package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mozzartbet.grckikino.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onPositive: (date: Long?) -> Unit,
    onNegative: () -> Unit
) {
    val dateState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onNegative,
        confirmButton = {
            Button(
                onClick = { onPositive(dateState.selectedDateMillis) }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            Button(
                onClick = onNegative
            ) {
                Text(stringResource(R.string.cancle))
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colors.surface
        )
    ) {
        DatePicker(
            state = dateState,
            showModeToggle = false,
            title = {
                Text(
                    text = stringResource(R.string.select_date),
                    modifier = Modifier
                        .padding(PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)),
                    color = MaterialTheme.colors.onSurface
                )
            },
            headline = {
                val dateFormatter = remember { DatePickerDefaults.dateFormatter() }
                val selectedDateMillis = dateState.selectedDateMillis

                val formattedDate = dateFormatter.formatDate(
                    selectedDateMillis,
                    Locale.getDefault()
                )

                val headlineText = formattedDate ?: ""

                androidx.compose.material3.Text(
                    text = headlineText,
                    modifier = Modifier.padding(
                        PaddingValues(start = 24.dp, end = 12.dp, bottom = 12.dp)
                    ),
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1
                )
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colors.surface,
                titleContentColor = MaterialTheme.colors.onSurface,
                weekdayContentColor = MaterialTheme.colors.onSurface,
                subheadContentColor = MaterialTheme.colors.onSurface,
                navigationContentColor = MaterialTheme.colors.onSurface,
                yearContentColor = MaterialTheme.colors.onSurface,
                currentYearContentColor = MaterialTheme.colors.onSurface,
                selectedYearContentColor = MaterialTheme.colors.onPrimary,
                selectedYearContainerColor = MaterialTheme.colors.primary,
                dayContentColor = MaterialTheme.colors.onSurface,
                selectedDayContentColor = MaterialTheme.colors.onPrimary,
                selectedDayContainerColor = MaterialTheme.colors.primary,
                todayContentColor = MaterialTheme.colors.onSurface,
                todayDateBorderColor = MaterialTheme.colors.primary,
                dayInSelectionRangeContentColor = MaterialTheme.colors.onSurface,
                dayInSelectionRangeContainerColor = MaterialTheme.colors.onSurface,
                dividerColor = MaterialTheme.colors.onSurface,
            )
        )
    }
}

@Preview
@Composable
private fun MyDatePickerDialogPreview() {
    MyDatePickerDialog({}, {})
}