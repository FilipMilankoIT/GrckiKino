package com.mozzartbet.grckikino.di

import android.content.Context
import com.mozzartbet.core.Repository
import com.mozzartbet.core.di.CoreProvider
import com.mozzartbet.grckikino.BuildConfig
import com.mozzartbet.grckikino.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = CoreProvider.getRepository(BuildConfig.API_BASE)

    @Singleton
    @Provides
    fun provideNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)
}