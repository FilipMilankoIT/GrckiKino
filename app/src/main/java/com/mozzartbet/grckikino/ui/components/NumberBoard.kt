package com.mozzartbet.grckikino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberBoard(selectedNumbers: Array<Boolean>, onNumberClicked: (Int) -> Unit) {

    val selectColor = MaterialTheme.colors.secondary

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
        columns = GridCells.Fixed(count = 10),
        contentPadding = PaddingValues(1.dp)
    ) {
        itemsIndexed(selectedNumbers) { index, isSelected ->
            val number = index + 1
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.0f)
                    .padding(1.dp)
                    .background(MaterialTheme.colors.background)
                    .clickable {
                        onNumberClicked(index)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .padding(1.dp)
                        .background(
                            if (isSelected) selectColor else Color.Transparent,
                            RoundedCornerShape(100f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        color = if (isSelected) MaterialTheme.colors.onSecondary
                        else MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NumberBoardPreview() {
    NumberBoard(Array(80) { false }) {}
}