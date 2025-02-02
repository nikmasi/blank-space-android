package com.example.blankspace.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class KorisniciViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateK())
    val uiState: StateFlow<UiStateK> = _uiState

    init {
        fetchKorisnici()
    }

    @SuppressLint("NewApi")
    fun formatDate(dateString: String): String {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm a", Locale.US)
        return zonedDateTime.format(formatter)
    }

    fun fetchKorisnici() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val response = repository.getKorisniciUklanjanje()

            val formattedResponse = response.map { item ->
                val formattedDate = item.poslednja_aktivnost?.let { formatDate(it) }
                item.copy(poslednja_aktivnost = formattedDate)  // Update the formatted date
            }
            _uiState.value = UiStateK(korisnici = formattedResponse, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateK(korisnici = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }
}

data class UiStateK(
    val korisnici: List<KorisniciResponse> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)