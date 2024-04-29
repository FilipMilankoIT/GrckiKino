package com.mozzartbet.api.di

import com.mozzartbet.api.Api
import com.mozzartbet.api.ApiImpl

object ApiProvider {

    fun getApi(baseUrl: String): Api = ApiImpl(baseUrl)
}