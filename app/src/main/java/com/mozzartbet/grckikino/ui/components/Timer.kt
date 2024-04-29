package com.mozzartbet.grckikino.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mozzartbet.grckikino.utils.TimeUtils.toTimerFormattedText
import kotlinx.coroutines.delay
import java.util.Date

@Composable
fun Timer(time: Long, onTimer: () -> Unit) {
    var leftTime by rememberSaveable {
        mutableLongStateOf(time - Date().time)
    }

    Text(
        text = leftTime.toTimerFormattedText(),
        color = if (leftTime < 60000) MaterialTheme.colors.error else Color.Unspecified
    )

    LaunchedEffect(leftTime) {
        delay(1000)
        if (leftTime < 1000) {
            delay(leftTime)
            onTimer()
        } else {
            leftTime = time - Date().time
        }
    }
}

@Preview
@Composable
private fun TimerPreview() {
    Timer(Date().time + 120000) {}
}