package com.segunfrancis.remote.models

import com.squareup.moshi.Json

data class HolidayRemote(
    @Json(name = "uuid")
    val id: String,
    val date: String,
    val name: String,
    val public: Boolean
)
