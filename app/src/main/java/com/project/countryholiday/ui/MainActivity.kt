package com.project.countryholiday.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.project.countryholiday.databinding.ActivityMainBinding
import com.segunfrancis.details.R
import dagger.hilt.android.AndroidEntryPoint
import com.project.countryholiday.R as AppResource

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(AppResource.id.fragmentContainerView) as NavHost

        navController = navHost.navController

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.holidayFragment) {
                binding.toolbar.apply {
                    setNavigationIcon(AppResource.drawable.ic_arrow_back)
                    val countryName = arguments?.getString("countryName", "")
                    title = countryName
                }
            } else {
                binding.toolbar.apply {
                    navigationIcon = null
                    setTitle(AppResource.string.app_name)
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
    }
}
