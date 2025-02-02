package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.Zanr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IzvodjacZanrViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateIZ())
    val uiState: StateFlow<UiStateIZ> = _uiState

    init {
        fetch()
    }

    fun fetch() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val response = repository.getZanrovi()
            val response2=repository.getIzvodjaci()
            _uiState.value = UiStateIZ(zanrovi = response, izvodjaci = response2, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateIZ(zanrovi = emptyList(), izvodjaci = emptyList(),
                isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateIZ(
    val zanrovi: List<Zanr> = emptyList(),
    val izvodjaci: List<Izvodjac> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)