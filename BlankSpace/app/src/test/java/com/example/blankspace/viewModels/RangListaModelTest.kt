package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.data.RepositoryInterface
import com.example.blankspace.data.retrofit.models.RangListaResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RangListaModelTest {

    private lateinit var viewModel: RangListaModel
    private val repository: RepositoryInterface = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRangLista updates uiState with data on success`() = runTest {
        val mockData = listOf(RangListaResponse(1,"Mirko","100"),
            RangListaResponse(2,"Stanko","30"))
        coEvery { repository.getRangLista() } returns mockData

        viewModel = RangListaModel(repository)

        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockData, state.rangLista)
            assertEquals(false, state.isRefreshing)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `fetchRangLista updates uiState with error on failure`() = runTest {
        val errorMessage = "Network Error"
        coEvery { repository.getRangLista() } throws Exception(errorMessage)

        viewModel = RangListaModel(repository)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(0, state.rangLista.size)
            assertEquals(false, state.isRefreshing)
            assertEquals(errorMessage, state.error)
        }
    }
}