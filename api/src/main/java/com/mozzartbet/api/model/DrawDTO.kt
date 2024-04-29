package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class DrawDTO(
    val gameId: Int?,
    val drawId: Int?,
    val drawTime: Long?,
    val status: String?,
    val drawBreak: Int?,
    val visualDraw: Int?,
    val pricePoints: PricePointsDTO?,
    val winningNumbers: WinningNumbersDTO?,
    val prizeCategories: List<PrizeCategoryDTO>?,
    val wagerStatistics: WagerStatisticsDTO?
)