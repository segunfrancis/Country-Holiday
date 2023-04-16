package com.segunfrancis.home

import androidx.fragment.app.Fragment

interface HomeNavigator {

    fun toHolidays(fragment: Fragment, countryCode: String)
}
