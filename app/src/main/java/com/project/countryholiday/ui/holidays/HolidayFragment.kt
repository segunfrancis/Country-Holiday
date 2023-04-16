package com.project.countryholiday.ui.holidays

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.project.countryholiday.R
import com.project.countryholiday.databinding.FragmentHolidaysBinding
import com.project.countryholiday.model.Holiday
import com.project.countryholiday.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

/*@AndroidEntryPoint*/
class HolidayFragment : Fragment(R.layout.fragment_holidays) {

    private val binding by viewBinding(FragmentHolidaysBinding::bind)

    private val args by navArgs<HolidayFragmentArgs>()

    /*@Inject*/
    lateinit var factory: HolidayViewModel.HolidayViewModelFactory

    private val viewModel by viewModels<HolidayViewModel> {
        HolidayViewModel.provideViewModelFactory(
            factory = factory,
            country = args.country
        )
    }

    private val holidayAdapter by lazy { HolidayAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.holidaysList.adapter = holidayAdapter
        setupObservers()
        setupClickListener()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is HolidayStates.Success -> handleSuccess(state.holidays)
                        is HolidayStates.Error -> handleError(state.error)
                        HolidayStates.Loading -> handleLoading()
                    }
                }
            }
        }
    }

    private fun setupClickListener() {
        binding.retryButton.setOnClickListener { viewModel.getHolidays() }
    }

    private fun handleLoading() = with(binding) {
        errorGroup.isGone = true
        progressBar.isVisible = true
    }

    private fun handleSuccess(holidays: List<Holiday>) = with(binding) {
        holidayAdapter.submitList(holidays)
        errorGroup.isGone = true
        progressBar.isGone = true
    }

    private fun handleError(errorMessage: String) = with(binding) {
        errorGroup.isVisible = true
        progressBar.isGone = true
        errorText.text = errorMessage
    }
}
