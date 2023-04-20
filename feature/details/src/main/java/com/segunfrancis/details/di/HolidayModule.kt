package com.segunfrancis.details.di

import com.segunfrancis.details.data.HolidayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HolidayModule {

    @Provides
    @Singleton
    fun provideHolidayService(retrofit: Retrofit): HolidayApi {
        return retrofit.create(HolidayApi::class.java)
    }
}
