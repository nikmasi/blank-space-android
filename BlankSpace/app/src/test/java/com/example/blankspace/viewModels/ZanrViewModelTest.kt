package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.data.retrofit.models.Zanr
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import android.util.Log
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.ContentRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ZanrViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: ContentRepository = mockk()
    private lateinit var viewModel: ZanrViewModel

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `fetchCategories success updates state with genres`() = runTest {
        val mockZanrovi = listOf(Zanr(id = 1, naziv = "Rok"), Zanr(id = 2, naziv = "Pop"))
        coEvery { repository.getZanrovi() } returns mockZanrovi

        viewModel = ZanrViewModel(repository)

        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockZanrovi, state.zanrovi)
            assertEquals(false, state.isRefreshing)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `fetchCategories failure updates state with error message`() = runTest {
        val errorMsg = "Greška na serveru"
        coEvery { repository.getZanrovi() } throws Exception(errorMsg)

        viewModel = ZanrViewModel(repository)
        advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(true, state.zanrovi.isEmpty())
            assertEquals(errorMsg, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }
}