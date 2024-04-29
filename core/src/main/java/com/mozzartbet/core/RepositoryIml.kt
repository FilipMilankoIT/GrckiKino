package com.mozzartbet.core

import android.util.Log
import com.mozzartbet.api.Api
import com.mozzartbet.api.model.ApiResult
import com.mozzartbet.api.model.StatusCodes
import com.mozzartbet.core.model.Draw
import com.mozzartbet.core.model.RepositoryResult
import com.mozzartbet.core.model.toModel

internal class RepositoryIml(
    private val api: Api
): Repository {

    companion object {
        private const val TAG = "RepositoryIml"
        private const val GRCKI_KINO_GAME_ID = 1100
    }

    override suspend fun getUpcomingKinoDraws(itemNum: Int): RepositoryResult<List<Draw>> {
        Log.i(TAG, "Fetching upcoming draws...")
        return when (val result = api.getUpcomingDraws(GRCKI_KINO_GAME_ID, itemNum)) {
            is ApiResult.Success -> {
                Log.i(TAG, "Successfully fetched upcoming draws")
                val draws = result.data.map { it.toModel() }
                RepositoryResult.Success(draws)
            }
            is ApiResult.Error -> {
                Log.i(TAG, "Failed to fetch upcoming draws: $result")
                if (result.code == StatusCodes.UNAUTHORIZED.value.toString()){
                    RepositoryResult.UnauthorisedError()
                }
                else RepositoryResult.Error(result.code, result.message)
            }
            is ApiResult.UnauthorisedError -> {
                Log.i(TAG, "Failed to fetch upcoming draws. Unauthorised error")
                RepositoryResult.UnauthorisedError()
            }
            is ApiResult.NetworkError -> {
                Log.i(TAG, "Failed to fetch upcoming draws. Network error: $result")
                RepositoryResult.NetworkError(result.message)
            }
            is ApiResult.UnknownError -> {
                Log.i(TAG, "Failed to fetch upcoming draws. Unknown error: $result")
                RepositoryResult.UnknownError(result.message)
            }
        }
    }

    override suspend fun getKinoDraw(drawId: Int): RepositoryResult<Draw> {
        Log.i(TAG, "Fetching draw $drawId from API...")
        return when (val result = api.getDraw(GRCKI_KINO_GAME_ID, drawId)) {
            is ApiResult.Success -> {
                Log.i(TAG, "Successfully fetched draw")
                val draw = result.data.toModel()
                RepositoryResult.Success(draw)
            }
            is ApiResult.Error -> {
                Log.i(TAG, "Failed to fetch draw. Api error: $result")
                if (result.code == StatusCodes.UNAUTHORIZED.value.toString()){
                    RepositoryResult.UnauthorisedError()
                }
                else RepositoryResult.Error(result.code, result.message)
            }
            is ApiResult.UnauthorisedError -> {
                Log.i(TAG, "Failed to fetch draw. Unauthorised error")
                RepositoryResult.UnauthorisedError()
            }
            is ApiResult.NetworkError -> {
                Log.i(TAG, "Failed to fetch draw. Network error: $result")
                RepositoryResult.NetworkError(result.message)
            }
            is ApiResult.UnknownError -> {
                Log.i(TAG, "Failed to fetch draw. Unknown error: $result")
                RepositoryResult.UnknownError(result.message)
            }
        }
    }

    override suspend fun getKinoDrawResults(
        fromDate: String,
        toDate: String
    ): RepositoryResult<List<Draw>> {
        Log.i(TAG, "Fetching draw results from $fromDate to $toDate...")
        return when (val result = api.getDrawResults(GRCKI_KINO_GAME_ID, fromDate, toDate)) {
            is ApiResult.Success -> {
                Log.i(TAG, "Successfully fetched draw results")
                val draws = result.data.content?.map { it.toModel() } ?: listOf()
                RepositoryResult.Success(draws)
            }
            is ApiResult.Error -> {
                Log.i(TAG, "Failed to fetch draw results. Api error: $result")
                if (result.code == StatusCodes.UNAUTHORIZED.value.toString()){
                    RepositoryResult.UnauthorisedError()
                }
                else RepositoryResult.Error(result.code, result.message)
            }
            is ApiResult.UnauthorisedError -> {
                Log.i(TAG, "Failed to fetch  draw results. Unauthorised error")
                RepositoryResult.UnauthorisedError()
            }
            is ApiResult.NetworkError -> {
                Log.i(TAG, "Failed to fetch draw results. Network error: $result")
                RepositoryResult.NetworkError(result.message)
            }
            is ApiResult.UnknownError -> {
                Log.i(TAG, "Failed to fetch draw results. Unknown error: $result")
                RepositoryResult.UnknownError(result.message)
            }
        }
    }
}