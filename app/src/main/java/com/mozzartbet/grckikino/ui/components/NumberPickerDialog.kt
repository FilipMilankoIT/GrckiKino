package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mozzartbet.grckikino.R

@Composable
fun NumberPickerDialog(
    minNumber: Int,
    maxNumber: Int,
    startNumber: Int = minNumber,
    onPositive: (Int) -> Unit,
    onNegative: () -> Unit
) {
    Dialog(
        onDismissRequest = onNegative
    ) {
        var number by rememberSaveable { mutableIntStateOf(startNumber) }

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.how_many_numbers)
                )

                Spacer(Modifier.size(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { number-- },
                        enabled = number > minNumber
                    ) {
                        Icon(
                            painterResource(R.drawable.left_arrow_icon),
                            "",
                            Modifier.size(50.dp),
                            MaterialTheme.colors.primary
                        )
                    }

                    Text(
                        text = number.toString(),
                        modifier = Modifier.width(30.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5
                    )

                    IconButton(
                        onClick = { number++ },
                        enabled = number < maxNumber
                    ) {
                        Icon(
                            painterResource(R.drawable.right_arrow_icon),
                            "",
                            Modifier.size(50.dp),
                            MaterialTheme.colors.primary
                        )
                    }
                }

                Spacer(Modifier.size(20.dp))

                Row {
                    Button(
                        onClick = { onNegative() },
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ) {
                        Text(text = stringResource(R.string.cancle))
                    }

                    Spacer(Modifier.size(40.dp))

                    Button(
                        onClick = { onPositive(number) },
                        modifier = Modifier
                            .height(40.dp)
                            .width(100.dp)
                    ) {
                        Text(text = stringResource(R.string.ok))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NumberPickerDialogPreview() {
    NumberPickerDialog(1, 15, 1, {}, {})
}