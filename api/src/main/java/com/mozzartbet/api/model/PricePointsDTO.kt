package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
class PricePointsDTO(
    val addOn: List<AddOnDTO>?,
    val amount: Double?
)