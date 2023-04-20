package com.segunfrancis.details.data

import com.segunfrancis.remote.BuildConfig
import com.segunfrancis.remote.models.Holidays
import retrofit2.http.GET
import retrofit2.http.Query

interface HolidayApi {

    @GET("holidays")
    suspend fun getHolidays(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("country") countryCode: String,
        @Query("year") year: String,
        @Query("pretty") isPretty: Boolean = true
    ): Holidays
}
