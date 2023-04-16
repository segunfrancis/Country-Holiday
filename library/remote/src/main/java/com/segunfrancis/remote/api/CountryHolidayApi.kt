package com.segunfrancis.remote.api

import com.segunfrancis.remote.models.Countries
import com.segunfrancis.remote.models.HolidayRequest
import com.segunfrancis.remote.models.Holidays
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CountryHolidayApi {

    @GET("Countries")
    suspend fun getCountries(): Countries

    @POST("List")
    suspend fun getHolidays(@Body params: HolidayRequest): Holidays
}
