package com.project.countryholiday.di

import com.project.countryholiday.model.Country
import com.project.countryholiday.ui.holidays.HolidayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val holidayModule = module {
    viewModel { (country: Country) ->
        HolidayViewModel(repository = get(), country = country)
    }
}
