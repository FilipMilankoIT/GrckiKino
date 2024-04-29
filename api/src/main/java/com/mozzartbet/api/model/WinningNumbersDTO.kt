package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class WinningNumbersDTO(
    val list: List<Int>?,
    val bonus: List<Int>?,
    val sidebets: SideBetsDTO?
)
