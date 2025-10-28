package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.PesmePoIzvodjacimaResponse
import com.example.blankspace.data.retrofit.models.StatistikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.models.Zanr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AdminStatistikaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AdminStatistikaUiState())
    val uiState: StateFlow<AdminStatistikaUiState> = _uiState

    private val _uiStatePesmePoIzvodjacima = MutableStateFlow(PesmePoIzvodjacimaUiState())
    val uiStatePesmePoIzvodjacima: StateFlow<PesmePoIzvodjacimaUiState> = _uiStatePesmePoIzvodjacima

    fun fetchAdminStatistika() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            //val request = UklanjanjeKorisnikaRequest()
            //val response = repository.uklanjanjeKorisnika(request)
            //_uiState.value = AdminStatistikaUiState(informacije = response, isRefreshing = false)

            _uiState.value =
                AdminStatistikaUiState(informacije = null, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                AdminStatistikaUiState(informacije = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchPesmePoIzvodjacima() = viewModelScope.launch {
        _uiStatePesmePoIzvodjacima.value = _uiStatePesmePoIzvodjacima.value.copy(isRefreshing = true)
        try {

            val response = repository.getPesmePoIzvodjacima()

            _uiStatePesmePoIzvodjacima.value =
                PesmePoIzvodjacimaUiState(pesmePoIzvodjacima = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStatePesmePoIzvodjacima.value =
                PesmePoIzvodjacimaUiState(pesmePoIzvodjacima = null, isRefreshing = false, error = e.localizedMessage)
        }
    }


}

data class AdminStatistikaUiState(
    val informacije: StatistikaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class PesmePoIzvodjacimaUiState(
    val pesmePoIzvodjacima: List<PesmePoIzvodjacimaResponse>?= emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)