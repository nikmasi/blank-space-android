package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.SuggestionRepository
import com.example.blankspace.data.retrofit.models.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.RequestBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PredloziViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: SuggestionRepository = mockk()
    private lateinit var viewModel: PredloziViewModel

    @Before
    fun setup() {
        viewModel = PredloziViewModel(repository)
    }

    @Test
    fun `fetchPredloziIzvodjaca updates state on success`() = runTest {
        val mockData = listOf(PredloziIzvodjacaResponse(
            id = 1,
            ime_izvodjaca = "ime",
            kor_ime = "kor",
            zan_naziv = "zanr",
            odgovor = "odg"
        ))
        coEvery { repository.getPredloziIzvodjaca() } returns mockData

        viewModel.fetchPredloziIzvodjaca()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockData, state.predloziIzvodjaca)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `odbijPredlogPesme updates uiStateP on success`() = runTest {
        val mockData = listOf(PredloziPesamaResponse(
            id = 2,
            naziv_pesme = "Pesma",
            izv_ime = "Izv",
            kor_ime = "Kor",
            zan_naziv = "zanr",
            odgovor = "ok"
        ))
        coEvery { repository.odbijPredlogPesme(any()) } returns mockData

        viewModel.odbijPredlogPesme(5)

        viewModel.uiStateP.test {
            val state = awaitItem()
            assertEquals(mockData, state.predloziPesama)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `dodajZanrSaFajlom updates uiStatePredlog on success`() = runTest {
        val mockResponse = DodajZanrResponse(
            odgovor = "Uspešno poslato"
        )
        val mockRequestBody = mockk<RequestBody>()

        coEvery {
            repository.dodajZanr(any(), any(), any(), any(), any(), any(), any())
        } returns mockResponse

        viewModel.dodajZanrSaFajlom(
            "Rock", "Izvodjač", "Pesma", "Stihovi 1",
            "Stihovi 2", "Lako", mockRequestBody
        )

        viewModel.uiStatePredlog.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.predlog)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `sacuvajIzvodjacPredlozi updates state immediately`() {
        viewModel.sacuvajIzvodjacPredlozi(1, "Bane", "Pop", "admin")

        val state = viewModel.uiStateIzvodjacPredlozi.value
        assertEquals(1, state.id)
        assertEquals("Bane", state.izvodjac)
        assertEquals("admin", state.korisnik)
    }

    @Test
    fun `resetDodajZanr clears the predlog in state`() = runTest {
        viewModel.resetDodajZanr()

        viewModel.uiStatePredlog.test {
            val state = awaitItem()
            assertEquals(null, state.predlog)
        }
    }
}