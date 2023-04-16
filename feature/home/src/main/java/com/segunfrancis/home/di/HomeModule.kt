package com.segunfrancis.home.di

import com.segunfrancis.home.data.HomeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
}
