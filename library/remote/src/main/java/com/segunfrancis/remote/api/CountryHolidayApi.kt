package com.segunfrancis.remote.api

import com.segunfrancis.remote.BuildConfig
import com.segunfrancis.remote.models.Countries
import com.segunfrancis.remote.models.Holidays
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CountryHolidayApi {

    @GET("countries")
    suspend fun getCountries(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("pretty") isPretty: Boolean = true
    ): Countries

    @POST("holidays")
    suspend fun getHolidays(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("country") countryCode: String,
        @Query("year") year: String,
        @Query("pretty") isPretty: Boolean = true
    ): Holidays
}
