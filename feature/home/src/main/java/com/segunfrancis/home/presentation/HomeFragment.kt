package com.segunfrancis.home.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.segunfrancis.home.HomeNavigator
import com.segunfrancis.home.R
import com.segunfrancis.home.databinding.FragmentHomesBinding
import com.segunfrancis.home.model.CountryHome
import com.segunfrancis.shared.extension.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_homes) {

    private val binding by viewBinding(FragmentHomesBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    @Inject lateinit var homeNavigator: HomeNavigator

    private val countryAdapter by lazy {
        CountryAdapter(onItemClick = {
            homeNavigator.toHolidays(this, it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        lifecycle.coroutineScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is HomeViewModel.HomeState.Error -> {
                            handleError(errorMessage = it.errorMessage)
                            handleLoading(isLoading = false)
                        }
                        HomeViewModel.HomeState.Loading -> handleLoading(isLoading = true)
                        is HomeViewModel.HomeState.Success -> {
                            setupCountriesList(it.countries)
                            handleLoading(isLoading = false)
                        }
                        HomeViewModel.HomeState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.retryButton.setOnClickListener { /*viewModel.getCountries()*/ }
    }

    private fun setupCountriesList(countries: List<CountryHome>) {
        binding.countriesList.apply {
            adapter = countryAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        countryAdapter.submitList(countries)
        binding.progressBar.isGone = true
        binding.errorGroup.isGone = true
    }

    private fun handleError(errorMessage: String?) = with(binding) {
        errorGroup.isVisible = true
        progressBar.isGone = true
        errorText.text = errorMessage
    }

    private fun handleLoading(isLoading: Boolean) = with(binding) {
        progressBar.isVisible = isLoading
    }
}
