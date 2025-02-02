package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeRequset
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PredlaganjePesmeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStatePP())
    val uiState: StateFlow<UiStatePP> = _uiState

    fun fetchPredlaganjePesme(korisnicko_ime: String, pesma:String,ime:String, zanr:String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = PredlaganjePesmeRequset(pesma,ime,zanr,korisnicko_ime)
            val response = repository.predlaganje_pesme(request)
            _uiState.value = UiStatePP(predlaganjepesme = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStatePP(predlaganjepesme = null, isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStatePP(
    val predlaganjepesme: PredlaganjePesmeResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)