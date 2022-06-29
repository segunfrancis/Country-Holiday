package com.project.countryholiday.model

import com.google.gson.annotations.SerializedName

data class HolidayRequest(
    @SerializedName("country_code")
    val countryCode: String,
    val year: Int = 2022
)
