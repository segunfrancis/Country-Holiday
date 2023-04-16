package com.project.countryholiday

import com.project.countryholiday.model.Country
import com.segunfrancis.details.model.HolidayHome
import com.project.countryholiday.model.HolidayRequest

val countries = listOf(
    Country("BZ", "Brazil"),
    Country("GBP", "United Kingdom")
)

val country = Country("US", "United States of America")

val holidayRequest = HolidayRequest(countryCode = "US")

val holidayHomeResponse = listOf(
    HolidayHome(
        countryCode = "US",
        date = "2022-12-25",
        localName = "Christmas Day",
        name = "Christmas Day",
        regions = listOf(),
        types = listOf("Public")
    )
)
