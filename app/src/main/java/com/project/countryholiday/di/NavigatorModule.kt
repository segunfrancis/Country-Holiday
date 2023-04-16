package com.project.countryholiday.di

import com.project.countryholiday.ui.AppNavigator
import com.segunfrancis.auth.AuthNavigator
import com.segunfrancis.home.HomeNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun bindAuthNavigator(navigator: AppNavigator): AuthNavigator

    @Binds
    abstract fun bindHomeNavigator(navigator: AppNavigator): HomeNavigator
}
