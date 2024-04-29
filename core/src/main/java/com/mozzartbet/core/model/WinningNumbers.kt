package com.mozzartbet.core.model

import androidx.annotation.Keep
import com.mozzartbet.api.model.WinningNumbersDTO

@Keep
data class WinningNumbers(
    val list: List<Int>
)

fun WinningNumbersDTO.toModel() = WinningNumbers(list ?: listOf())
