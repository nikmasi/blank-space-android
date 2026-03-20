package com.example.blankspace.viewModels

import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.AdminRepository
import com.example.blankspace.data.retrofit.models.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DodavanjeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AdminRepository = mockk()
    private lateinit var viewModel: DodavanjeViewModel

    @Before
    fun setup() {
        viewModel = DodavanjeViewModel(repository)
    }

    @Test
    fun `fetchPredloziIzvodjaca updates uiState on success`() = runTest {
        val mockResponse = listOf(PredloziIzvodjacaResponse(
            id = 1,
            ime_izvodjaca = "dzej",
            kor_ime = "stanko",
            zan_naziv = "narodna",
            odgovor = "ok",
        ))
        coEvery { repository.getPredloziIzvodjaca() } returns mockResponse

        viewModel.fetchPredloziIzvodjaca()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(mockResponse, state.predloziIzvodjaca)
        assertEquals(false, state.isRefreshing)
    }

    @Test
    fun `proveraPostojanja updates state with response`() = runTest {
        val mockResponse = ProveraDaLiPostojiResponse(odgovor = "Postoji")
        coEvery { repository.provera_da_li_postoji(any()) } returns mockResponse

        viewModel.proveraPostojanja("rok", "zanr")
        advanceUntilIdle()

        assertEquals(mockResponse, viewModel.uiStateProveraPostojanja.value.proveraDaLiPostoji)
    }

    @Test
    fun `dodajZanrSaFajlom handles multipart upload success`() = runTest {
        // Arrange
        val mockResponse = DodajZanrResponse(odgovor = "Uspeh")
        val dummyRequestBody = "test audio content".toRequestBody()

        coEvery {
            repository.dodajZanr(any(), any(), any(), any(), any(), any(), any())
        } returns mockResponse

        viewModel.dodajZanrSaFajlom(
            "rok", "AC/DC", "T.N.T",
            "nepoznato", "poznato", "Lako", dummyRequestBody
        )
        advanceUntilIdle()

        val state = viewModel.uiStateDodajZanr.value
        assertEquals(mockResponse, state.dodajZanr)
        assertEquals(false, state.isRefreshing)
    }

    @Test
    fun `resetDodajZanr clears the response in state`() = runTest {
        viewModel.resetDodajZanr()

        assertEquals(null, viewModel.uiStateDodajZanr.value.dodajZanr)
    }
}