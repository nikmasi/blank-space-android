package com.example.blankspace.viewModels

import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.ContentRepository
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
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
class IzvodjaciZanraViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ContentRepository = mockk()
    private lateinit var viewModel: IzvodjaciZanraViewModel

    private val mockIzvodjaci = listOf(
        IzvodjaciZanra(id = 1, ime = "Bon Jovi"),
        IzvodjaciZanra(id = 2, ime = "Nirvana")
    )

    @Before
    fun setup() {
        viewModel = IzvodjaciZanraViewModel(repository)
    }

    @Test
    fun `fetchIzvodjaciZanra by ID updates state correctly`() = runTest {
        val zanrId = 5
        coEvery { repository.getIzvodjaciZanra(any<Zanr>()) } returns mockIzvodjaci

        viewModel.fetchIzvodjaciZanra(zanrId)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(mockIzvodjaci, state.izvodjaci)
        assertEquals(false, state.isRefreshing)
        assertEquals(null, state.error)
    }

    @Test
    fun `fetchIzvodjaciZanraNaziv by Name updates state correctly`() = runTest {
        val zanrNaziv = "Rock"
        coEvery { repository.dohvati_izvodjace_zanra(any<ZanrNazivRequest>()) } returns mockIzvodjaci

        viewModel.fetchIzvodjaciZanraNaziv(zanrNaziv)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(mockIzvodjaci, state.izvodjaci)
        assertEquals(false, state.isRefreshing)
    }

    @Test
    fun `fetchIzvodjaciZanra failure updates state with error`() = runTest {
        val errorMsg = "Neuspešno učitavanje"
        coEvery { repository.getIzvodjaciZanra(any()) } throws Exception(errorMsg)

        viewModel.fetchIzvodjaciZanra(1)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(true, state.izvodjaci.isEmpty())
        assertEquals(errorMsg, state.error)
    }
}