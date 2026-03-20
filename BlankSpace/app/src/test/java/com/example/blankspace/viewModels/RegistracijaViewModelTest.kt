package com.example.blankspace.viewModels

import android.content.Context
import android.content.SharedPreferences
import app.cash.turbine.test
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.blankspace.MainDispatcherRule
import com.example.blankspace.data.AuthRepository

@OptIn(ExperimentalCoroutinesApi::class)
class RegistracijaViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mockk()
    private val context: Context = mockk(relaxed = true)
    private val sharedPrefs: SharedPreferences = mockk(relaxed = true)
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    private lateinit var viewModel: RegistracijaViewModel

    @Before
    fun setup() {
        // Moramo "ubiti" statičke pozive za EncryptedSharedPreferences jer oni ne rade u unit testovima
        mockkStatic(EncryptedSharedPreferences::class)
        mockkStatic(MasterKeys::class)

        every { MasterKeys.getOrCreate(any()) } returns "some_key"
        every {
            EncryptedSharedPreferences.create(any(), any(), any(), any(), any())
        } returns sharedPrefs

        // Setup za snimanje tokena
        every { sharedPrefs.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor

        viewModel = RegistracijaViewModel(repository, context)
    }

    @Test
    fun `fetchRegistracija success saves token and updates state`() = runTest {
        val mockResponse = RegistracijaResponse(
            access = "fake_jwt_token",
            refresh = "",
            ime = "Ime",
            korisnicko_ime = "user",
            tip = "tip",
            odgovor = "odg"
        )
        coEvery { repository.postRegistracija(any()) } returns mockResponse

        viewModel.fetchRegistracija("Ime", "user", "lozinka", "lozinka", "Pitanje", "Odgovor")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(mockResponse, state.registration)
            assertEquals(false, state.isRefreshing)

            verify { editor.putString("access_token", "fake_jwt_token") }
            verify { editor.apply() }
        }
    }

    @Test
    fun `fetchRegistracija failure updates state with error`() = runTest {
        val errorMsg = "Korisničko ime već postoji"
        coEvery { repository.postRegistracija(any()) } throws Exception(errorMsg)

        viewModel.fetchRegistracija("Ime", "user", "123", "123", "P", "O")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(null, state.registration)
            assertEquals(errorMsg, state.error)
        }
    }
}