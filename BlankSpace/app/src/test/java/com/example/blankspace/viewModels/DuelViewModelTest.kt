package com.example.blankspace.viewModels

import android.util.Log
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DuelViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mockk()
    private lateinit var viewModel: DuelViewModel

    @Before
    fun setup() {
        // 1. Rešavamo problem sa Log klasom
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        viewModel = DuelViewModel(repository)
    }

    @Test
    fun `generisiSifru updates state and writes room code on success`() = runTest {
        val mockResponse = GenerisiSifruResponse(sifra = 1234, stihovi = "Neki stihovi")
        coEvery { repository.generisiSifru(any()) } returns mockResponse

        viewModel.generisiSifru("Korisnik1")
        advanceUntilIdle()

        assertEquals(1234, viewModel.uiStateSifSobe.value.sifraResponse?.sifra)
        assertEquals(1234, viewModel.sifraSobe.value.sifra)
    }

    @Test
    fun `proveriSifru updates state on success`() = runTest {
        // 2. Usaglašavamo mock podatke i assert
        val ocekivaniStihovi = "Neki test stihovi"
        val mockResponse = ProveriSifruResponse(
            stihovi = ocekivaniStihovi,
            zvuk = "url",
            crtice = "_ _",
            runda = 1,
            poeni = 6,
            error = "null"
        )
        coEvery { repository.proveriSifruSobe(any()) } returns mockResponse

        viewModel.proveriSifru("Igrac2", 1234)
        advanceUntilIdle()

        // Proveravamo da li je vrednost ista kao u mock-u
        assertEquals(ocekivaniStihovi, viewModel.uiStateProveriSifru.value.proveriSifru?.stihovi)
        assertEquals(false, viewModel.uiStateProveriSifru.value.isRefreshing)
    }

    @Test
    fun `fetchKrajDuela updates complex state with poeni_runde`() = runTest {
        val mockPoeniRunde = listOf(listOf(10, 20), listOf(15, 25))
        val mockResponse = KrajDuelaResponse(
            poeni_runde = mockPoeniRunde,
            poeni = listOf(10, 20),
            igrac1 = "i1",
            igrac2 = "i2",
            ulogovan = true
        )
        coEvery { repository.krajDuela(any()) } returns mockResponse

        viewModel.fetchKrajDuela(10, 1234, listOf(10, 5), 1, "da")
        advanceUntilIdle()

        val state = viewModel.uiStateKrajDuela.value
        assertEquals(mockPoeniRunde, state.poeni_runde)
        assertEquals(false, state.isRefreshing)
    }
}