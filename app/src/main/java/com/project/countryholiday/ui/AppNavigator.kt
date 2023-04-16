package com.project.countryholiday.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.countryholiday.NavMainDirections
import com.segunfrancis.auth.AuthNavigator
import com.segunfrancis.home.HomeNavigator
import javax.inject.Inject

class AppNavigator @Inject constructor() : AuthNavigator, HomeNavigator {
    override fun navigateToHome(fragment: Fragment) {
        fragment.findNavController().navigate(NavMainDirections.toHomeFragment())
    }

    override fun toHolidays(fragment: Fragment, countryCode: String) {
        fragment.findNavController().navigate(NavMainDirections.toDetailsFragment(countryCode))
    }
}
