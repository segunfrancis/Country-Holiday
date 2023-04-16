package com.segunfrancis.details.data

import com.segunfrancis.local.db.CountryHolidayDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HolidayRepository @Inject constructor(
    private val local: CountryHolidayDao?,
    private val remote: HolidayApi,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(countryCode: String) = withContext(dispatcher) {
        remote.getHolidays(countryCode = countryCode, year = "2023")
    }
}
