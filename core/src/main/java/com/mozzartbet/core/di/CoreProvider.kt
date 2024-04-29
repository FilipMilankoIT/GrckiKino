package com.mozzartbet.core.di

import com.mozzartbet.api.di.ApiProvider
import com.mozzartbet.core.Repository
import com.mozzartbet.core.RepositoryIml

object CoreProvider {

    fun getRepository(baseUrl: String): Repository {
        return RepositoryIml(ApiProvider.getApi(baseUrl))
    }
}