package com.mozzartbet.api

import com.mozzartbet.api.model.DrawDTO
import com.mozzartbet.api.model.GetDrawResultsResponseDTO
import retrofit2.Response
import retrofit2.http.*

internal interface ApiRetrofit {

    @GET("{gameId}/upcoming/{itemNum}")
    suspend fun getUpcomingDraws(
        @Path("gameId") gameId: Int,
        @Path("itemNum") itemNum: Int
    ): Response<List<DrawDTO>>

    @GET("{gameId}/{drawId}")
    suspend fun getDraw(
        @Path("gameId") gameId: Int,
        @Path("drawId") drawId: Int
    ): Response<DrawDTO>

    @GET("{gameId}/draw-date/{fromDate}/{toDate}")
    suspend fun getDrawResults(
        @Path("gameId") gameId: Int,
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): Response<GetDrawResultsResponseDTO>
}