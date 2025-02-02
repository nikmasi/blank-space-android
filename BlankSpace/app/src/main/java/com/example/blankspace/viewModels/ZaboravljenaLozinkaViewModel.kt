package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.models.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZaboravljenaLozinkaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateZL())
    val uiState: StateFlow<UiStateZL> = _uiState

    private val _uiStateP = MutableStateFlow(UiStateZLP())
    val uiStateP: StateFlow<UiStateZLP> = _uiStateP

    private val _uiStateNL = MutableStateFlow(UiStateNL())
    val uiStateNL: StateFlow<UiStateNL> = _uiStateNL

    fun fetchZaboravljenaLozinka(korisnicko_ime: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        Log.d("ZABORAVLJENA LOZi","ovde")
        try {
            val request = ZaboravljenaLozinkaRequest(korisnicko_ime)
            val response = repository.postZaboravljenaLozinka(request)
            _uiState.value = UiStateZL(zaboravljenaLozinka = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStateZL(zaboravljenaLozinka = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchZaboravljenaLozinkaPitanje(korisnicko_ime: String,odgovor:String) = viewModelScope.launch {
        _uiStateP.value = _uiStateP.value.copy(isRefreshing = true)
        try {
            val request = ZaboravljenaLozinkaPitanjeRequest(korisnicko_ime,odgovor)
            val response = repository.postZaboravljenaLozinkaPitanje(request)
            _uiStateP.value = UiStateZLP(zaboravljenaLozinkaPitanje = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateP.value =
                UiStateZLP(zaboravljenaLozinkaPitanje = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchNovaLozinka(korisnicko_ime: String,lozinka:String,potvrda: String) = viewModelScope.launch {
        _uiStateNL.value = _uiStateNL.value.copy(isRefreshing = true)
        try {
            val request = NovaLozinkaRequest(korisnicko_ime,lozinka,potvrda)
            val response = repository.postNovaLozinka(request)
            _uiStateNL.value = UiStateNL(novaLozinka = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateNL.value = UiStateNL(novaLozinka = null, isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateZL(
    val zaboravljenaLozinka: ZaboravljenaLozinkaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateZLP(
    val zaboravljenaLozinkaPitanje: ZaboravljenaLozinkaPitanjeResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateNL(
    val novaLozinka: NovaLozinkaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)