package com.example.blankspace.viewModels

import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MojProfilViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mockk()
    private lateinit var viewModel: MojProfilViewModel

    @Before
    fun setup() {
        viewModel = MojProfilViewModel(repository)
    }

    @Test
    fun `fetchMojProfil updates uiState with profile data on success`() = runTest {
        // Arrange
        val mockProfile = MojProfilResponse(
            ime = "Stanko",
            prezime = "Markovic",
            licni_poeni = 12,
            rank = "122",
            rang_poeni = 55,
            korisnicko_ime = "stanko"
        )
        coEvery { repository.getMojProfilData(any()) } returns mockProfile

        viewModel.fetchMojProfil("stanko")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockProfile, state.mojprofil)
            assertEquals(false, state.isRefreshing)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `fetchMojProfil updates uiState with error message on failure`() = runTest {
        val errorMessage = "Korisnik nije pronađen"
        coEvery { repository.getMojProfilData(any()) } throws Exception(errorMessage)

        viewModel.fetchMojProfil("nepoznat_user")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(null, state.mojprofil)
            assertEquals(errorMessage, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }
}