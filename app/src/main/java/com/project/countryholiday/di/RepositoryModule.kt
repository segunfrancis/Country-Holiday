package com.project.countryholiday.di

import com.project.countryholiday.repository.HolidayRepository
import com.project.countryholiday.repository.HolidayRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: HolidayRepositoryImpl): HolidayRepository
}
