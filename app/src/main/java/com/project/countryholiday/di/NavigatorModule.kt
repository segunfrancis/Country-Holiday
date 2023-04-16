package com.project.countryholiday.di

import com.project.countryholiday.ui.AppNavigator
import com.segunfrancis.auth.AuthNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun bindAppNavigator(navigator: AppNavigator): AuthNavigator
}
