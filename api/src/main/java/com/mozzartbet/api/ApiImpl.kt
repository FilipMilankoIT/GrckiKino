package com.mozzartbet.api

import android.util.Log
import com.google.gson.Gson
import com.mozzartbet.api.model.ApiResult
import com.mozzartbet.api.model.ErrorResponseDTO
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

internal class ApiImpl(baseUrl: String): Api {

    private val service: ApiRetrofit

    companion object {
        private const val TAG = "ApiImpl"
    }

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val requestInterceptor = object : Interceptor {

            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build()
                return chain.proceed(request)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .build()

        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRetrofit::class.java)
    }

    override suspend fun getUpcomingDraws(gameId: Int, itemNum: Int) = getResponse {
        service.getUpcomingDraws(gameId, itemNum)
    }

    override suspend fun getDraw(gameId: Int, drawId: Int) = getResponse {
        service.getDraw(gameId, drawId)
    }

    override suspend fun getDrawResults(
        gameId: Int,
        fromDate: String,
        toDate: String
    ) = getResponse {
        service.getDrawResults(gameId, fromDate, toDate)
    }

    private suspend fun <T> getResponse(apiCall: suspend () -> Response<T>): ApiResult<T> {
        try {
            val result = apiCall()
            return if (result.isSuccessful) {
                val body = result.body()
                if (body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.UnknownError("")
                }
            } else {
                val errorBody = result.errorBody()
                if (errorBody != null) {
                    val errorResponse: ErrorResponseDTO =
                        Gson().fromJson(errorBody.charStream(), ErrorResponseDTO::class.java)
                    Log.e(TAG, "${errorResponse.code}: ${errorResponse.message}")
                    if (errorResponse.code.isNullOrEmpty() && (
                                errorResponse.message == "Unauthorized" ||
                                errorResponse.message == "Unauthenticated."
                    ))
                        ApiResult.UnauthorisedError()
                    else ApiResult.Error(
                        errorResponse.code ?: "",
                        errorResponse.message ?: ""
                    )
                } else {
                    ApiResult.UnknownError("")
                }
            }
        } catch (throwable: Throwable) {
            val message = throwable.message ?: ""
            Log.e(TAG, message)
            return if(throwable is IOException)
                    ApiResult.NetworkError(message)
                else
                    ApiResult.UnknownError(message)
        }
    }
}