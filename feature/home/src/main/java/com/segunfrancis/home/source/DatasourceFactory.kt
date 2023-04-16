package com.segunfrancis.home.source

import com.segunfrancis.local.db.CountryHolidayDao
import com.segunfrancis.remote.api.CountryHolidayApi
import javax.inject.Inject

class DatasourceFactory @Inject constructor(
    private val remoteSource: CountryHolidayApi,
    private val local: CountryHolidayDao?
) {
    fun remote() = remoteSource
    fun local() = local
}
