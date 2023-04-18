package com.segunfrancis.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.segunfrancis.details.data.HolidayRepository
import com.segunfrancis.details.model.HolidayHome
import com.segunfrancis.remote.models.HolidayRemote
import com.segunfrancis.shared.extension.handleThrowable
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HolidayViewModel @AssistedInject constructor(
    private val repository: HolidayRepository,
    @Assisted private val countryCode: String
) : ViewModel() {

    @AssistedFactory
    interface HolidayViewModelFactory {
        fun create(countryCode: String): HolidayViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideViewModelFactory(
            factory: HolidayViewModelFactory,
            countryCode: String
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(countryCode) as T
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
            val holidays = repository(countryCode).holidays
            _uiState.value = HolidayStates.Success(holidays.map { it.toHolidayHome() })
        }
    }

    private fun HolidayRemote.toHolidayHome(): HolidayHome {
        return HolidayHome(id, date, name, public)
    }
}

sealed class HolidayStates {
    object Loading : HolidayStates()
    data class Error(val error: String) : HolidayStates()
    data class Success(val holidays: List<HolidayHome>) : HolidayStates()
}
