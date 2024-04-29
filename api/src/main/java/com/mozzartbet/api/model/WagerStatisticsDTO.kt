package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class WagerStatisticsDTO(
    val columns: Int?,
    val wagers: Int?,
    val addOn: List<AddOnDTO>?
)
