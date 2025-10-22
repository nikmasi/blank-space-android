package com.example.blankspace.viewModels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IzvodjaciZanraViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateIZU())
    val uiState: StateFlow<UiStateIZU> = _uiState

    fun fetchIzvodjaciZanra(zanr: Int) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val z = Zanr(zanr,"")
            val response = repository.getIzvodjaciZanra(z)
            _uiState.value = UiStateIZU(izvodjaci = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateIZU(izvodjaci = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchIzvodjaciZanraNaziv(zanrN: String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val z = ZanrNazivRequest(zanr=zanrN)
            val response = repository.dohvati_izvodjace_zanra(z)
            _uiState.value = UiStateIZU(izvodjaci = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateIZU(izvodjaci = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateIZU(
    val izvodjaci: List<IzvodjaciZanra> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)