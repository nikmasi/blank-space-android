package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.models.Zanr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UklanjanjeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateUklanjanjeKorisnika())
    val uiState: StateFlow<UiStateUklanjanjeKorisnika> = _uiState

    private val _uiStateZanr = MutableStateFlow(UiStateUklanjanjeZanra())
    val uiStateZanr: StateFlow<UiStateUklanjanjeZanra> = _uiStateZanr

    private val _uiStateIzvodjac = MutableStateFlow(UiStateUklanjanjeIzvodjaca())
    val uiStateIzvodjac: StateFlow<UiStateUklanjanjeIzvodjaca> = _uiStateIzvodjac

    private val _uiStatePesma = MutableStateFlow(UiStateUklanjanjePesme())
    val uiStatePesma: StateFlow<UiStateUklanjanjePesme> = _uiStatePesma

    fun fetchUklanjanjeKorisnika(korisnicko_ime:String) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = UklanjanjeKorisnikaRequest(korisnicko_ime)
            val response = repository.uklanjanjeKorisnika(request)
            _uiState.value = UiStateUklanjanjeKorisnika(uklanjanjeKorisnika = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStateUklanjanjeKorisnika(uklanjanjeKorisnika = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchUklanjanjeZanra(zanr:Int) = viewModelScope.launch {
        _uiStateZanr.value = _uiStateZanr.value.copy(isRefreshing = true)
        try {
            val request = UklanjanjeZanraRequest(zanr)
            val response = repository.uklanjanjeZanra(request)
            _uiStateZanr.value = UiStateUklanjanjeZanra(uklanjanjeZanra = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateZanr.value =
                UiStateUklanjanjeZanra(uklanjanjeZanra = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchUklanjanjeIzvodjac(izvodjac:Int) = viewModelScope.launch {
        _uiStateIzvodjac.value = _uiStateIzvodjac.value.copy(isRefreshing = true)
        try {
            val request = UklanjanjeIzvodjacaRequest(izvodjac)
            val response = repository.uklanjanjeIzvodjaca(request)
            _uiStateIzvodjac.value = UiStateUklanjanjeIzvodjaca(uklanjanjeIzvodjaca = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateIzvodjac.value =
                UiStateUklanjanjeIzvodjaca(uklanjanjeIzvodjaca = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchUklanjanjePesme(pesma:Int) = viewModelScope.launch {
        _uiStatePesma.value = _uiStatePesma.value.copy(isRefreshing = true)
        try {
            val request = UklanjanjePesmeRequest(pesma)
            val response = repository.uklanjanjePesme(request)
            _uiStatePesma.value = UiStateUklanjanjePesme(uklanjanjePesme = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStatePesma.value =
                UiStateUklanjanjePesme(uklanjanjePesme = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    private val _uiStateIzvodjacaZanra = MutableStateFlow(UiStateIZU())
    val uiStateIzvodjacaZanra: StateFlow<UiStateIZU> = _uiStateIzvodjacaZanra

    private val _uiStatePesmeIzv = MutableStateFlow(PesmeIzv())
    val uiStatePesmeIzv: StateFlow<PesmeIzv> = _uiStatePesmeIzv

    fun postaviPesmeIzv(izvodjac_id: Int,ime: String){
        _uiStatePesmeIzv.value=PesmeIzv(izvodjac_id,ime)
    }

    fun dohvatiIzvodjaceZanrova(zanr_id:Int,imeZanr:String)= viewModelScope.launch {
        _uiStateIzvodjacaZanra.value = _uiStateIzvodjacaZanra.value.copy(isRefreshing = true)
        try {
            val request = Zanr(zanr_id,imeZanr)
            val response = repository.getIzvodjaciZanra(request)
            _uiStateIzvodjacaZanra.value = UiStateIZU(izvodjaci = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateIzvodjacaZanra.value =
                UiStateIZU(izvodjaci = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    private val _uiStatePesmeIzvodjaca = MutableStateFlow(UiStatePesmeIzvodjaca())
    val uiStatePesmeIzvodjaca: StateFlow<UiStatePesmeIzvodjaca> = _uiStatePesmeIzvodjaca

    fun dohvatiPesmeIzvodjaca(izvodjac_id: Int,ime:String,zanr_id: Int)= viewModelScope.launch {
        _uiStatePesmeIzvodjaca.value = _uiStatePesmeIzvodjaca.value.copy(isRefreshing = true)
        try {
            val request = Izvodjac(izvodjac_id,ime,zanr_id)
            val response = repository.getPesmeIzvodjaca(request)
            _uiStatePesmeIzvodjaca.value = UiStatePesmeIzvodjaca(pesmeIzvodjaca = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStatePesmeIzvodjaca.value =
                UiStatePesmeIzvodjaca(pesmeIzvodjaca = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

}

data class UiStateUklanjanjeKorisnika(
    val uklanjanjeKorisnika: UklanjanjeKorisnikaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateUklanjanjeZanra(
    val uklanjanjeZanra: UklanjanjeZanraResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateUklanjanjeIzvodjaca(
    val uklanjanjeIzvodjaca: UklanjanjeIzvodjacaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateUklanjanjePesme(
    val uklanjanjePesme: UklanjanjePesmeResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStatePesmeIzvodjaca(
    val pesmeIzvodjaca: List<PesmeIzvodjaca> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class PesmeIzv(
    val izvodjac_id: Int?=null,
    val ime: String?=null
)