package com.segunfrancis.home.data

import com.segunfrancis.remote.BuildConfig
import com.segunfrancis.remote.api.CountryHolidayApi
import com.segunfrancis.remote.models.Countries
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi : CountryHolidayApi {

    @GET("countries")
    suspend fun getCountries(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("pretty") isPretty: Boolean = true
    ): Countries
}
