package com.project.countryholiday.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.countryholiday.NavMainDirections
import com.segunfrancis.auth.AuthNavigator
import javax.inject.Inject

class AppNavigator @Inject constructor() : AuthNavigator {
    override fun navigateToHome(fragment: Fragment) {
        fragment.findNavController().navigate(NavMainDirections.toHomeFragment())
    }
}
