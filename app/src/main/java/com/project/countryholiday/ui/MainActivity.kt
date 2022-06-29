package com.project.countryholiday.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.project.countryholiday.R
import com.project.countryholiday.databinding.ActivityMainBinding
import com.project.countryholiday.model.Country
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHost

        navController = navHost.navController

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.holidayFragment) {
                binding.toolbar.apply {
                    setNavigationIcon(R.drawable.ic_arrow_back)
                    val country = arguments?.get("country") as Country
                    title = country.name
                }
            } else {
                binding.toolbar.apply {
                    navigationIcon = null
                    setTitle(R.string.app_name)
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener { navController.navigateUp() }
    }
}
