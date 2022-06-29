package com.project.countryholiday.repository

import com.project.countryholiday.data.HolidayService
import com.project.countryholiday.model.BaseCountryResponse
import com.project.countryholiday.model.BaseHolidayResponse
import com.project.countryholiday.model.HolidayRequest
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class HolidayRepositoryImpl @Inject constructor(
    private val api: HolidayService,
    private val dispatcher: CoroutineDispatcher
) : HolidayRepository {
    override suspend fun getCountries(): BaseCountryResponse {
        return withContext(dispatcher) { api.getCountries() }
    }

    override suspend fun getHolidays(params: HolidayRequest): BaseHolidayResponse {
        return withContext(dispatcher) { api.getHolidays(params) }
    }
}
