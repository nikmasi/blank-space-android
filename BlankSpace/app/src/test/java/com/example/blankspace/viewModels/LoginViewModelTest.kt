package com.example.blankspace.viewModels

import android.content.Context
import android.content.SharedPreferences
import app.cash.turbine.test
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.LoginResponse
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.yield

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: Repository = mockk()
    private val context: Context = mockk(relaxed = true)
    private val sharedPrefs: SharedPreferences = mockk(relaxed = true)
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        mockkStatic(EncryptedSharedPreferences::class)
        mockkStatic(MasterKeys::class)
        every { MasterKeys.getOrCreate(any()) } returns "test_key"
        every {
            EncryptedSharedPreferences.create(any(), any(), any(), any(), any())
        } returns sharedPrefs

        every { sharedPrefs.edit() } returns editor
        every { editor.putString(any(), any()) } returns editor
    }

    @Test
    fun `izloguj_se clears credentials and login state`() = runTest {
        every { sharedPrefs.getString(any(), any()) } returns null
        viewModel = LoginViewModel(repository, context, sharedPrefs)

        viewModel.izloguj_se()

        verify { editor.putString("user_key", "") }
        verify { editor.putString("password_key", "") }
        assertEquals(null, viewModel.uiState.value.login)
    }

    @Test
    fun `setNotificationTime updates uiStateNotifikacija correctly`() {
        every { sharedPrefs.getString(any(), any()) } returns null
        viewModel = LoginViewModel(repository, context, sharedPrefs)

        viewModel.setNotificationTime(9, 5)

        assertEquals("09:05", viewModel.uiStateNotifikacija.value.vreme)
    }
}