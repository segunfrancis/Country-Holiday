package com.project.countryholiday.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.project.countryholiday.R
import com.project.countryholiday.databinding.FragmentHomeBinding
import com.project.countryholiday.model.Country
import com.project.countryholiday.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val countryAdapter by lazy {
        CountryAdapter(onItemClick = {
            findNavController().navigate(HomeFragmentDirections.toHolidayFragment(country = it))
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeStates.Success -> setupCountriesList(it.countries)
                        is HomeStates.Error -> handleError(it.errorMessage)
                        HomeStates.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.errorGroup.isGone = true
                        }
                    }
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.retryButton.setOnClickListener { viewModel.getCountries() }
    }

    private fun setupCountriesList(countries: List<Country>) {
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

    private fun handleError(errorMessage: String) = with(binding) {
        errorGroup.isVisible = true
        progressBar.isGone = true
        errorText.text = errorMessage
    }
}
