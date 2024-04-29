package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class PrizeCategoryDTO(
    val id: Int?,
    val divident: Double?,
    val winners: Int?,
    val distributed: Double?,
    val jackpot: Double?,
    val fixed: Double?,
    val categoryType: Int?,
    val gameType: String?
)
