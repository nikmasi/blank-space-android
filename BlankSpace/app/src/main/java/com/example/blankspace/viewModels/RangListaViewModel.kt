package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.ContentRepository
import com.example.blankspace.data.retrofit.models.RangListaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RangListaModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateRL())
    val uiState: StateFlow<UiStateRL> = _uiState

    init {
        fetchRangLista()
    }

    fun fetchRangLista() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val response = contentRepository.getRangLista()

            _uiState.value = _uiState.value.copy(
                rangLista = response,
                isRefreshing = false
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isRefreshing = false,
                error = e.localizedMessage
            )
        }
    }
}

data class UiStateRL(
    val rangLista: List<RangListaResponse> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)