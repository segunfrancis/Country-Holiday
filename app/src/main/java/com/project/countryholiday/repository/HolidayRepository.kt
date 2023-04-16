package com.project.countryholiday.repository

import com.project.countryholiday.model.BaseCountryResponse
import com.project.countryholiday.model.BaseHolidayResponse
import com.project.countryholiday.model.HolidayRequest

interface HolidayRepository {
    suspend fun getCountries(): BaseCountryResponse
    suspend fun getHolidays(params: HolidayRequest): BaseHolidayResponse
}
