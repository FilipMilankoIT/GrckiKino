package com.mozzartbet.core.model

import androidx.annotation.Keep
import com.mozzartbet.api.model.DrawDTO

@Keep
data class Draw(
    val gameID: Int,
    val drawID: Int,
    val drawTime: Long,
    val winningNumbers: WinningNumbers?
)

fun DrawDTO.toModel() = Draw(
    gameId ?: -1,
    drawId ?: -1,
    drawTime ?: 0L,
    winningNumbers?.toModel()
)