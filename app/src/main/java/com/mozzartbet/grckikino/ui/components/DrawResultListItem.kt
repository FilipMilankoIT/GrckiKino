package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mozzartbet.core.model.Draw
import com.mozzartbet.core.model.WinningNumbers
import com.mozzartbet.grckikino.R
import com.mozzartbet.grckikino.utils.TimeUtils.toFormattedDateTime

@Composable
fun DrawResultListItem(draw: Draw) {
    Card {
        Column(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(12.dp)
        ) {
            val formattedDrawTim = draw.drawTime.toFormattedDateTime("dd.MM. HH:mm")
            Text(
                text = "${stringResource(R.string.draw_time)}: $formattedDrawTim"
            )

            Text(
                text = "${stringResource(R.string.draw)}: ${draw.drawID}"
            )

            Spacer(Modifier.size(12.dp))

            val winningNumbers = draw.winningNumbers?.list?.sorted() ?: listOf()

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f)
                    .background(MaterialTheme.colors.surface),
                columns = GridCells.Fixed(count = 10),
                contentPadding = PaddingValues(1.dp)
            ) {
                itemsIndexed(winningNumbers) { _, number ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.0f)
                            .padding(1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.0f)
                                .padding(1.dp)
                                .background(
                                    MaterialTheme.colors.secondary,
                                    RoundedCornerShape(100f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = number.toString(),
                                color = MaterialTheme.colors.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DrawResultListItemPreview() {
    DrawResultListItem(
        draw = Draw(
            1100,
            1085070,
            1714210800000L,
            WinningNumbers(
                listOf(4, 5, 8, 11, 15, 16, 19, 20, 23, 26, 33, 45, 47, 54, 60, 66, 67, 69, 77, 78),
            )
        )
    )
}