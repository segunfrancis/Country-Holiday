package com.segunfrancis.local.di

import android.content.Context
import com.segunfrancis.local.db.CountryHolidayDao
import com.segunfrancis.local.db.CountryHolidayDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideDao(@ApplicationContext context: Context): CountryHolidayDao? {
        return CountryHolidayDatabase.getDatabase(context)?.getDao()
    }
}
