package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.MojProfilRequest
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MojProfilViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateMP())
    val uiState: StateFlow<UiStateMP> = _uiState

    fun fetchMojProfil(korisnicko_ime: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = MojProfilRequest(korisnicko_ime)
            val response = repository.getMojProfilData(request)
            _uiState.value = UiStateMP(mojprofil = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStateMP(mojprofil = null, isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateMP(
    val mojprofil:MojProfilResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)