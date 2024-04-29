package com.mozzartbet.api.model

import androidx.annotation.Keep

@Keep
data class GetDrawResultsResponseDTO(
    val content: List<DrawDTO>?,
    val totalPages: Int?,
    val totalElements: Int?,
    val last: Boolean?,
    val numberOfElements: Int?,
    val first: Boolean?,
    val size: Int?,
    val number: Int?
)