package com.example.blankspace.viewModels

import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.AdminRepository
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import com.example.blankspace.data.retrofit.models.KorisnikPregledResponse
import com.example.blankspace.data.retrofit.models.Mecevi
import com.example.blankspace.data.retrofit.models.PesmeMeceva
import com.example.blankspace.data.retrofit.models.Protivnik
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class KorisniciViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AdminRepository = mockk()
    private lateinit var viewModel: KorisniciViewModel

    private val mockKorisnici = listOf(
        KorisniciResponse(
            korisnicko_ime = "user1",
            poslednja_aktivnost = "2023-10-27T10:15:30Z"
        )
    )

    @Before
    fun setup() {
        coEvery { repository.getKorisniciUklanjanje() } returns mockKorisnici
    }

    @Test
    fun `init calls fetchKorisnici and formats dates correctly`() = runTest {
        viewModel = KorisniciViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(1, state.korisnici.size)

        val expectedDate = "October 27, 2023, 10:15 AM"
        assertEquals(expectedDate, state.korisnici[0].poslednja_aktivnost)
    }


    @Test
    fun `fetchInformacijeKorisnikPregled updates state on success`() = runTest {
        val mockPregled = KorisnikPregledResponse(
            ukupnoDuela = 12,
            ukupnoPobeda = 11,
            ukupnoPoraza = 9,
            ukupnoNereseno = 7,
            protivnici =listOf(Protivnik("pera",11,10)),
            mecevi = listOf(Mecevi("ime",5,5)),
            pesmeMeceva = listOf(PesmeMeceva("pesma1","izv",1))
        )
        coEvery { repository.getPregledKorisnik(any()) } returns mockPregled

        viewModel = KorisniciViewModel(repository)
        advanceUntilIdle()

        viewModel.fetchInformacijeKorisnikPregled("user1")

        advanceUntilIdle()

        val finalState = viewModel.uiStateKorisnikPregled.value
        assertEquals(mockPregled, finalState.informacije)
        assertEquals(false, finalState.isRefreshing)
        assertEquals(null, finalState.error)
    }

    @Test
    fun `formatDate returns expected string pattern`() {
        viewModel = KorisniciViewModel(repository)
        val inputDate = "2024-05-20T20:00:00Z"
        val result = viewModel.formatDate(inputDate)

        assertEquals("May 20, 2024, 8:00 PM", result)
    }
}