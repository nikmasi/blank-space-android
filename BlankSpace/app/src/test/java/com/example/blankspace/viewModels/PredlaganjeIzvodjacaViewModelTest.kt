package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PredlaganjeIzvodjacaViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mockk()
    private lateinit var viewModel: PredlaganjeIzvodjacaViewModel

    @Before
    fun setup() {
        viewModel = PredlaganjeIzvodjacaViewModel(repository)
    }

    @Test
    fun `fetchPredlaganjeIzvodjaca updates uiState on success`() = runTest {
        val mockResponse = PredlaganjeIzvodjacaResponse(odgovor = "Success")
        coEvery { repository.predlaganje_izvodjaca(any()) } returns mockResponse

        viewModel.fetchPredlaganjeIzvodjaca("admin", "Bane", "Pop")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.predlaganjeIzvodjaca)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `fetchPredlaganjePretrazi updates uiStatePretrazi on success`() = runTest {
        val mockResponse = PredlaganjePretraziResponse(odgovor = "Found")
        coEvery { repository.predlaganje_pretrazi(any()) } returns mockResponse

        viewModel.fetchPredlaganjePretrazi("admin", "Search Query")

        viewModel.uiStatePretrazi.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.predlaganjePretrazi)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `fetchPretragaPredlaganje updates uiStateWebScrapper with list of songs`() = runTest {
        val mockSongs = listOf(
            WebScrapperResponse(
                naslov = "Pesma 1", tekst = "Tekst 1",
                izvodjac = "izv1"
            ),
            WebScrapperResponse(naslov = "Pesma 2", tekst = "Tekst 2", izvodjac = "izv2")
        )
        coEvery { repository.web_scrapper(any()) } returns mockSongs

        viewModel.fetchPretragaPredlaganje("admin", "Ime", "Neki tekst")

        viewModel.uiStateWebScrapper.test {
            val state = awaitItem()
            assertEquals(2, state.pesme.size)
            assertEquals("Pesma 1", state.pesme[0].naslov)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `fetchPretragaPredlaganje updates uiStateWebScrapper with error on failure`() = runTest {
        val errorMsg = "Scrapper failed"
        coEvery { repository.web_scrapper(any()) } throws Exception(errorMsg)

        viewModel.fetchPretragaPredlaganje("admin", "Ime", "Tekst")

        viewModel.uiStateWebScrapper.test {
            val state = awaitItem()
            assertEquals(0, state.pesme.size)
            assertEquals(errorMsg, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }
}