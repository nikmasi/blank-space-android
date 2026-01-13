package com.example.blankspace.viewModels

import android.content.Context
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.*
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
class IgraSamViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mockk()
    private val context: Context = mockk()
    private lateinit var viewModel: IgraSamViewModel

    @Before
    fun setup() {
        viewModel = IgraSamViewModel(repository)
    }

    @Test
    fun `fetchIgraSamData success updates state and listaBilo`() = runTest {
        val mockResponse = IgraSamResponse(
            listaBilo = listOf(1, 2, 3),
            stihpoznat = listOf("st","ga","Agds"),
            crtice="",
            tacno = "",
            izvodjac= "izv",
            zvuk= "zv",
            pesma ="ps",
            runda = 1,
            poeni =11,
        )
        coEvery { repository.getIgraSamData(any()) } returns mockResponse

        viewModel.fetchIgraSamData(listOf("1", "2"), "Lako", 1, 10, emptyList(), context)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(mockResponse, state.igrasam)
        assertEquals(false, state.isRefreshing)

        assertEquals(listOf(1, 2, 3), viewModel.IgraSamLista.value.igraSamLista)
    }

    @Test
    fun `fetchKrajIgre success updates uiStateKI`() = runTest {
        val mockKI = KrajIgreResponse(
            poeni = 100,
            tip = "tip",
            ulogovan = "ul"
        )
        coEvery { repository.krajIgre(any()) } returns mockKI

        viewModel.fetchKrajIgre("Bane", 100)
        advanceUntilIdle()

        val state = viewModel.uiStateKI.value
        assertEquals(mockKI, state.krajIgre)
        assertEquals(false, state.isRefreshing)
    }

    @Test
    fun `downloadAudio failure updates error in uiState`() = runTest {
        val errorMsg = "No internet"
        coEvery { repository.getAudio(any()) } throws Exception(errorMsg)

        viewModel.downloadAudio("some_url", context)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assert(state.error?.contains("Greška pri preuzimanju MP3 fajla") == true)
    }
}