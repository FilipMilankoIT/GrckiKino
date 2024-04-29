package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class AddOnDTO(
    val amount: Double?,
    val gameType: String?
)
