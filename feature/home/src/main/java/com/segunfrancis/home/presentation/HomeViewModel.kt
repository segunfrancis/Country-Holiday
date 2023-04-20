package com.segunfrancis.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.home.model.CountryHome
import com.segunfrancis.home.usecase.CountryListUseCase
import com.segunfrancis.home.usecase.CountryListUseCase.CountryListScenario.*
import com.segunfrancis.shared.extension.handleThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: CountryListUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeState>(HomeState.Idle)

    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _uiState.update { HomeState.Error(throwable.localizedMessage) }
    }

    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch(exceptionHandler) {
            useCase()
                .catch { error ->
                    if (error !is CancellationException)
                        _uiState.update { HomeState.Error(error.handleThrowable()) }
                }
                .collect { scenario ->
                    when (scenario) {
                        is Error -> {
                            _uiState.update { HomeState.Error(scenario.errorMessage) }
                        }
                        is NetworkErrorDatabaseSuccess -> {
                            _uiState.update { HomeState.Success(scenario.countries) }
                        }
                        is Database -> _uiState.update { HomeState.Success(scenario.countries) }
                        Loading -> _uiState.update { HomeState.Loading }
                    }
                }
        }
    }

    sealed class HomeState {
        data class Success(val countries: List<CountryHome>) : HomeState()
        data class Error(val errorMessage: String?) : HomeState()
        object Loading : HomeState()
        object Idle : HomeState()
    }
}
