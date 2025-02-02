package com.example.blankspace.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistracijaViewModel @Inject constructor(
    private val repository: Repository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateR())
    val uiState: StateFlow<UiStateR> = _uiState

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun fetchRegistracija(ime_i_prezime: String, korisnicko_ime: String,lozinka:String,potvrda_lozinke:String,pitanje:String,odgovor:String)
    = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {

            val request = RegistracijaRequest(ime_i_prezime, korisnicko_ime,lozinka,potvrda_lozinke,pitanje,odgovor)
            val response = repository.postRegistracija(request)
            // Sačuvaj JWT token u EncryptedSharedPreferences
            saveToken(response.access)

            _uiState.value = UiStateR(registration  = response, isRefreshing = false)

        } catch (e: Exception) {
            _uiState.value = UiStateR(registration = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString("access_token", token)
            .apply()
    }
}


data class UiStateR(
    val registration: RegistracijaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)