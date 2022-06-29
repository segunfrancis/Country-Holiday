package com.project.countryholiday.ui.home

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.project.countryholiday.countries
import com.project.countryholiday.model.BaseCountryResponse
import com.project.countryholiday.repository.HolidayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(context = testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getCountriesTest() = testScope.runTest {
        val mockRepo = mock<HolidayRepository> {
            onBlocking { getCountries() } doReturn BaseCountryResponse(countries)
        }

        val viewModel = HomeViewModel(repository = mockRepo)
        viewModel.getCountries()
        val result = viewModel.uiState.value
        if (result is HomeStates.Success) {
            assert(result.countries.isNotEmpty())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
