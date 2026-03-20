package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.SuggestionRepository
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PredlaganjePesmeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: SuggestionRepository = mockk()
    private lateinit var viewModel: PredlaganjePesmeViewModel

    @Before
    fun setup() {
        viewModel = PredlaganjePesmeViewModel(repository)
    }

    @Test
    fun `fetchPredlaganjePesme updates uiState with success`() = runTest {
        val mockResponse = PredlaganjePesmeResponse(
            odgovor = "sent"
        )
        coEvery { repository.predlaganje_pesme(any()) } returns mockResponse

        viewModel.fetchPredlaganjePesme("user123", "Neka Pesma", "Izvođač", "Pop")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.predlaganjepesme)
            assertEquals(false, state.isRefreshing)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `fetchPredlaganjePesme updates uiState with error message on failure`() = runTest {
        val errorMessage = "Network timeout"
        coEvery { repository.predlaganje_pesme(any()) } throws Exception(errorMessage)

        viewModel.fetchPredlaganjePesme("user123", "Pesma", "Ime", "Zanr")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(null, state.predlaganjepesme)
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }
}