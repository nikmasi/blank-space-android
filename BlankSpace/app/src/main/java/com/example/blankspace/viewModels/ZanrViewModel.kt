package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.Zanr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZanrViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateZ())
    val uiState: StateFlow<UiStateZ> = _uiState

    init {
        fetchCategories()
    }

    fun fetchCategories() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val response = repository.getZanrovi()
            Log.d("API_RESPONSE", response.toString())
            _uiState.value = UiStateZ(zanrovi = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateZ(zanrovi = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateZ(
    val zanrovi: List<Zanr> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)