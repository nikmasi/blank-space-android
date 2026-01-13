package com.example.blankspace.viewModels

import android.content.Context
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.MyRoomRepository
import com.example.blankspace.data.room.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DatabaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: MyRoomRepository = mockk()
    private val context: Context = mockk()
    private lateinit var viewModel: DatabaseViewModel

    @Before
    fun setup() {
        mockkStatic(android.util.Log::class)
        every { android.util.Log.d(any(), any()) } returns 0

        every { repository.allZanrovi } returns kotlinx.coroutines.flow.flowOf(emptyList())
        every { repository.allSobe } returns kotlinx.coroutines.flow.flowOf(emptyList())

        viewModel = DatabaseViewModel(repository)
    }

    @Test
    fun `fetchIgraOfflineData returns error when no songs found`() = runTest {
        coEvery { repository.getStihoviPoTeziniIZanrovima(any(), any()) } returns emptyList()

        viewModel.fetchIgraOfflineData(listOf("1"), "Easy", 1, 0, emptyList(), context)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Nema više dostupnih stihova", state.error)
    }

    @Test
    fun `insert calls repository insert`() = runTest {
        val zanr = ZanrEntity(id = 1, naziv = "Rock")
        coEvery { repository.insert(any()) } just Runs

        viewModel.insert(zanr)
        advanceUntilIdle()

        coVerify { repository.insert(zanr) }
    }
}