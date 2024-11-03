package com.radlance.presentation

import com.radlance.test.extension.TestDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TimeViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var timeViewModel: TimeViewModel

    @Before
    fun setup() {
        timeViewModel = TimeViewModel()
    }

    @Test
    fun timeUiState_assertDefaultValues() {
        with(TimeUiState()) {
            assertEquals(0, hour)
            assertEquals(0, minute)
            assertEquals(0, seconds)
            assertEquals("", timeZone)
        }
    }

    @Test
    fun timeViewModel_startUpdatingTime_updatesUiState() = runTest {
        val baseState = TimeUiState()

        timeViewModel.startUpdatingTime()

        val initialState = timeViewModel.timeUiState.value
        assertNotEquals(baseState, initialState)

        val startTime = System.currentTimeMillis()
        val currentState = timeViewModel.timeUiState.first { it != initialState }
        val endTime = System.currentTimeMillis()

        timeViewModel.stopUpdatingTime()

        val executionTime = endTime - startTime
        assertTrue(executionTime <= 1000L)
        assertNotEquals("", initialState.timeZone)

        assert(currentState.hour in 0..23)
        assert(currentState.minute in 0..59)
        assert(currentState.seconds in 0..59)

        assertNotEquals(initialState, currentState)
    }
}