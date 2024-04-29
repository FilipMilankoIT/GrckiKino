package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
class SideBetsDTO(
    val evenNumbersCount: Int?,
    val oddNumbersCount: Int?,
    val winningColumn: Int?,
    val winningParity: String?,
    val oddNumbers: List<Int>?,
    val evenNumbers: List<Int>?,
    val columnNumbers: Map<String, List<Int>>?
)