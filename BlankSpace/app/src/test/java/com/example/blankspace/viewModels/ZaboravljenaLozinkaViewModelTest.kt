package com.example.blankspace.viewModels

import android.util.Log
import app.cash.turbine.test
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.AuthRepository
import com.example.blankspace.data.retrofit.models.*
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ZaboravljenaLozinkaViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mockk()
    private lateinit var viewModel: ZaboravljenaLozinkaViewModel

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        viewModel = ZaboravljenaLozinkaViewModel(repository)
    }

    @Test
    fun `fetchZaboravljenaLozinka updates uiState with success`() = runTest {
        val mockResponse = ZaboravljenaLozinkaResponse(
            korisnicko_ime = "ljubica",
            pitanje_lozinka = "Ime ljubimca?",
            odgovor_lozinka = "geri",
            tip = "tip",
            odgovor = "odgovor"
        )
        coEvery { repository.postZaboravljenaLozinka(any()) } returns mockResponse

        viewModel.fetchZaboravljenaLozinka("ljubica")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.zaboravljenaLozinka)
            assertEquals(false, state.isRefreshing)
            assertEquals(null, state.error)
        }
    }

    @Test
    fun `fetchZaboravljenaLozinkaPitanje updates uiStateP with success`() = runTest {
        val mockResponse = ZaboravljenaLozinkaPitanjeResponse(odgovor = "OK")
        coEvery { repository.postZaboravljenaLozinkaPitanje(any()) } returns mockResponse

        viewModel.fetchZaboravljenaLozinkaPitanje("test_user", "Rex")

        viewModel.uiStateP.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.zaboravljenaLozinkaPitanje)
            assertEquals(false, state.isRefreshing)
        }
    }

    @Test
    fun `fetchNovaLozinka updates uiStateNL with error on failure`() = runTest {
        val errorMsg = "Lozinke se ne podudaraju"
        coEvery { repository.postNovaLozinka(any()) } throws Exception(errorMsg)

        viewModel.fetchNovaLozinka("test_user", "123", "1234")

        viewModel.uiStateNL.test {
            val state = awaitItem()
            assertEquals(null, state.novaLozinka)
            assertEquals(errorMsg, state.error)
            assertEquals(false, state.isRefreshing)
        }
    }
}