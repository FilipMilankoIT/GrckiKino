package com.mozzartbet.grckikino.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimeText(time: Long, format: String = "HH:mm", color: Color = Color.Unspecified) {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    Text(
        text = formatter.format(Date(if (time < 0) 0 else time)),
        color = color
    )
}

@Preview
@Composable
private fun TimeTextPreview() {
    TimeText(1714210800000L)
}