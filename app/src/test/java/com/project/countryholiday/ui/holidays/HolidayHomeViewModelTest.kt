package com.project.countryholiday.ui.holidays

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.project.countryholiday.country
import com.project.countryholiday.holidayRequest
import com.project.countryholiday.holidayHomeResponse
import com.project.countryholiday.model.BaseHolidayResponse
import com.project.countryholiday.repository.HolidayRepository
import com.segunfrancis.details.HolidayStates
import com.segunfrancis.details.HolidayViewModel
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
class HolidayHomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(context = testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getHolidaysTest() = testScope.runTest {
        val mockRepo = mock<HolidayRepository> {
            onBlocking { getHolidays(params = holidayRequest) } doReturn BaseHolidayResponse(
                holidayHomes = holidayHomeResponse
            )
        }

        val viewModel = HolidayViewModel(repository = mockRepo, country = country)
        viewModel.getHolidays()
        val result = viewModel.uiState.value
        if (result is HolidayStates.Success) {
            assert(result.holidays.isNotEmpty())
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
