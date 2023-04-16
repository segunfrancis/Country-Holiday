package com.segunfrancis.home.data

import com.segunfrancis.local.db.CountryHolidayDao
import javax.inject.Inject

class DatasourceFactory @Inject constructor(
    private val remoteSource: HomeApi,
    private val local: CountryHolidayDao?
) {
    fun remote() = remoteSource
    fun local() = local
}
