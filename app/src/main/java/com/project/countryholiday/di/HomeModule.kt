package com.project.countryholiday.di

import com.project.countryholiday.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    viewModel { HomeViewModel(repository = get()) }
}
