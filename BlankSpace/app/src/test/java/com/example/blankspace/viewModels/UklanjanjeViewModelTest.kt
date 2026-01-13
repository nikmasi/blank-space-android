package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.RepositoryInterface
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
class UklanjanjeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: RepositoryInterface = mockk()
    private lateinit var viewModel: UklanjanjeViewModel

    @Before
    fun setup() {
        viewModel = UklanjanjeViewModel(repository)
    }


    @Test
    fun `fetchUklanjanjeKorisnika updates uiState with success`() = runTest {
        val mockResponse = UklanjanjeKorisnikaResponse(
            odgovor = "Ok"
        )
        coEvery { repository.uklanjanjeKorisnika(any()) } returns mockResponse

        viewModel.fetchUklanjanjeKorisnika("stanislav")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.uklanjanjeKorisnika)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `fetchUklanjanjePesme updates uiStatePesma with error on failure`() = runTest {
        val errorMsg = "Pesma ne postoji"
        coEvery { repository.uklanjanjePesme(any()) } throws Exception(errorMsg)

        viewModel.fetchUklanjanjePesme(101)

        viewModel.uiStatePesma.test {
            val state = awaitItem()
            assertEquals(null, state.uklanjanjePesme)
            assertEquals(errorMsg, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }


    @Test
    fun `dohvatiIzvodjaceZanrova updates uiStateIzvodjacaZanra with data`() = runTest {
        val mockIzvodjaci = listOf(IzvodjaciZanra(1, "Izvodjac 1"))
        coEvery { repository.getIzvodjaciZanra(any()) } returns mockIzvodjaci

        viewModel.dohvatiIzvodjaceZanrova(10, "Rock")

        viewModel.uiStateIzvodjacaZanra.test {
            val state = awaitItem()
            assertEquals(mockIzvodjaci, state.izvodjaci)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `dohvatiPesmeIzvodjaca updates uiStatePesmeIzvodjaca on success`() = runTest {
        val mockPesme = listOf(PesmeIzvodjaca(id = 1, naziv = "Adore you"))
        coEvery { repository.getPesmeIzvodjaca(any()) } returns mockPesme

        viewModel.dohvatiPesmeIzvodjaca(1, "Harry Styles", 10)

        viewModel.uiStatePesmeIzvodjaca.test {
            val state = awaitItem()
            assertEquals(mockPesme, state.pesmeIzvodjaca)
            assertEquals(false, state.isRefreshing)
        }
    }


    @Test
    fun `postaviPesmeIzv updates uiStatePesmeIzv immediately`() {
        viewModel.postaviPesmeIzv(5, "Harry Styles")

        val state = viewModel.uiStatePesmeIzv.value
        assertEquals(5, state.izvodjac_id)
        assertEquals("Harry Styles", state.ime)
    }
}