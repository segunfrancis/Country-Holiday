package com.project.countryholiday.data

import com.project.countryholiday.model.BaseCountryResponse
import com.project.countryholiday.model.BaseHolidayResponse
import com.project.countryholiday.model.HolidayRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HolidayService {

    @GET("Countries")
    suspend fun getCountries(): BaseCountryResponse

    @POST("List")
    suspend fun getHolidays(@Body params: HolidayRequest): BaseHolidayResponse
}
