package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.AuthRepository
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import com.example.blankspace.data.storage.TokenManagerInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistracijaViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManagerInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateR())
    val uiState: StateFlow<UiStateR> = _uiState

    fun fetchRegistracija(ime_i_prezime: String, korisnicko_ime: String,lozinka:String,potvrda_lozinke:String,pitanje:String,odgovor:String)
    = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {

            val request = RegistracijaRequest(ime_i_prezime, korisnicko_ime,lozinka,potvrda_lozinke,pitanje,odgovor)
            val response = authRepository.postRegistracija(request)
            // Sačuvaj JWT token u EncryptedSharedPreferences
            saveToken(response.access)

            _uiState.value = _uiState.value.copy(
                registration = response,
                isRefreshing = false,
                error = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isRefreshing = false,
                error = e.localizedMessage
            )
        }
    }

    private fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }
}

data class UiStateR(
    val registration: RegistracijaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)