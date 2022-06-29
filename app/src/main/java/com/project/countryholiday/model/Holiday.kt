package com.project.countryholiday.model

import com.google.gson.annotations.SerializedName

data class Holiday(
    @SerializedName("country_code")
    val countryCode: String,
    val date: String,
    @SerializedName("local_name")
    val localName: String,
    val name: String,
    val regions: List<String>,
    val types: List<String>
)