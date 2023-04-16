package com.segunfrancis.remote.models

import com.squareup.moshi.Json

data class HolidayRequest(
    @Json(name ="country_code")
    val countryCode: String,
    val year: Int = 2022
)
