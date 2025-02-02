package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaRequset
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PredlaganjeIzvodjacaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStatePI())
    val uiState: StateFlow<UiStatePI> = _uiState

    fun fetchPredlaganjeIzvodjaca(korisnicko_ime: String, ime:String, zanr:String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = PredlaganjeIzvodjacaRequset(ime,zanr,korisnicko_ime)
            val response = repository.predlaganje_izvodjaca(request)
            _uiState.value = UiStatePI(predlaganjeIzvodjaca = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStatePI(predlaganjeIzvodjaca = null, isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStatePI(
    val predlaganjeIzvodjaca: PredlaganjeIzvodjacaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)