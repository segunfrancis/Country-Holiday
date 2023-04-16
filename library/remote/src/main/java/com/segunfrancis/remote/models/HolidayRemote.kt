package com.segunfrancis.remote.models

import com.squareup.moshi.Json

data class HolidayRemote(
    @Json(name = "country_code")
    val countryCode: String,
    val date: String,
    @Json(name = "local_name")
    val localName: String,
    val name: String,
    val regions: List<String>,
    val types: List<String>
)
