package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mozzartbet.core.model.Draw

@Composable
fun DrawListItem(draw: Draw, onTimer: () -> Unit, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeText(draw.drawTime)

            Spacer(Modifier.weight(1f))

            Timer(draw.drawTime, onTimer)
        }
    }
}

@Preview
@Composable
private fun DrawListItemPreview() {
    DrawListItem(
        draw = Draw(
            1100,
            1085070,
            1714210800000L,
            null
        ),
        onTimer = {},
        onClick = {}
    )
}