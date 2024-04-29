package com.mozzartbet.api

import com.mozzartbet.api.model.ApiResult
import com.mozzartbet.api.model.DrawDTO
import com.mozzartbet.api.model.GetDrawResultsResponseDTO

interface Api {

    suspend fun getUpcomingDraws(gameId: Int, itemNum: Int): ApiResult<List<DrawDTO>>

    suspend fun getDraw(gameId: Int, drawId: Int): ApiResult<DrawDTO>

    suspend fun getDrawResults(gameId: Int, fromDate: String, toDate: String): ApiResult<GetDrawResultsResponseDTO>
}