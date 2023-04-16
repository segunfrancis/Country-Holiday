package com.project.countryholiday.ui.holidays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.countryholiday.model.Country
import com.project.countryholiday.model.Holiday
import com.project.countryholiday.model.HolidayRequest
import com.project.countryholiday.repository.HolidayRepository
import com.project.countryholiday.util.handleThrowable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HolidayViewModel /*@AssistedInject*/ constructor(
    private val repository: HolidayRepository,
    /*@Assisted*/ private val country: Country
) : ViewModel() {

    /*@AssistedFactory*/
    interface HolidayViewModelFactory {
        fun create(country: Country): HolidayViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideViewModelFactory(
            factory: HolidayViewModelFactory,
            country: Country
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(country) as T
                }
            }
    }

    private val _uiState = MutableStateFlow<HolidayStates>(HolidayStates.Loading)
    val uiState: StateFlow<HolidayStates> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
        _uiState.value = HolidayStates.Error(throwable.handleThrowable())
    }

    init {
        getHolidays()
    }

    fun getHolidays() {
        _uiState.value = HolidayStates.Loading
        viewModelScope.launch(exceptionHandler) {
            val holidays =
                repository.getHolidays(HolidayRequest(countryCode = country.code)).holidays
            _uiState.value = HolidayStates.Success(holidays)
        }
    }
}

sealed class HolidayStates {
    object Loading : HolidayStates()
    data class Error(val error: String) : HolidayStates()
    data class Success(val holidays: List<Holiday>) : HolidayStates()
}
