package com.example.blankspace.viewModels

import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.ContentRepository
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.Zanr
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class IzvodjacZanrViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ContentRepository = mockk()
    private lateinit var viewModel: IzvodjacZanrViewModel

    @Test
    fun `init calls fetch and updates state with genres and artists`() = runTest {
        val mockZanrovi = listOf(Zanr(id = 1, naziv = "Rock"))
        val mockIzvodjaci = listOf(Izvodjac(
            id = 1, ime = "Bon Jovi",
            zan = 1,
        ))

        coEvery { repository.getZanrovi() } returns mockZanrovi
        coEvery { repository.getIzvodjaci() } returns mockIzvodjaci

        viewModel = IzvodjacZanrViewModel(repository)

        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(mockZanrovi, finalState.zanrovi)
        assertEquals(mockIzvodjaci, finalState.izvodjaci)
        assertEquals(false, finalState.isRefreshing)
        assertEquals(null, finalState.error)
    }

    @Test
    fun `fetch failure updates state with error message`() = runTest {
        val errorMessage = "Network Error"
        coEvery { repository.getZanrovi() } throws Exception(errorMessage)

        viewModel = IzvodjacZanrViewModel(repository)
        advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertEquals(true, finalState.zanrovi.isEmpty())
        assertEquals(errorMessage, finalState.error)
        assertEquals(false, finalState.isRefreshing)
    }
}