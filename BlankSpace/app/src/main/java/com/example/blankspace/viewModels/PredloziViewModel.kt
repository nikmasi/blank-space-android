package com.example.blankspace.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.DodajZanrResponse
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.PredloziPesamaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziPesamaResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PredloziViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStatePredloziIzv())
    val uiState: StateFlow<UiStatePredloziIzv> = _uiState

    private val _uiStateP = MutableStateFlow(UiStatePredloziPes())
    val uiStateP: StateFlow<UiStatePredloziPes> = _uiStateP

    fun fetchPredloziIzvodjaca() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val response = repository.getPredloziIzvodjaca()
            _uiState.value = UiStatePredloziIzv(predloziIzvodjaca = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStatePredloziIzv(predloziIzvodjaca = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun odbijPredlogIzvodjaca(id:Int) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = PredloziIzvodjacaOdbijRequest(id)
            val response = repository.odbijPredlogIzvodjaca(request)
            _uiState.value = UiStatePredloziIzv(predloziIzvodjaca = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value =
                UiStatePredloziIzv(predloziIzvodjaca = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchPredloziPesama() = viewModelScope.launch {
        _uiStateP.value = _uiStateP.value.copy(isRefreshing = true)
        try {
            val response = repository.getPredloziPesme()
            _uiStateP.value = UiStatePredloziPes(predloziPesama = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateP.value =
                UiStatePredloziPes(predloziPesama = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun odbijPredlogPesme(id:Int) = viewModelScope.launch {
        _uiStateP.value = _uiStateP.value.copy(isRefreshing = true)
        try {
            val request = PredloziPesamaOdbijRequest(id)
            val response = repository.odbijPredlogPesme(request)
            _uiStateP.value = UiStatePredloziPes(predloziPesama = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateP.value =
                UiStatePredloziPes(predloziPesama = emptyList(), isRefreshing = false, error = e.localizedMessage)
        }
    }

    private val _uiStateIzvodjacPredlozi = MutableStateFlow(IzvodjacPredlozi())
    val uiStateIzvodjacPredlozi: StateFlow<IzvodjacPredlozi> = _uiStateIzvodjacPredlozi

    fun sacuvajIzvodjacPredlozi(id:Int,izvodjac: String,zanr: String,korisnik: String){
        _uiStateIzvodjacPredlozi.value= IzvodjacPredlozi(id,izvodjac,zanr,korisnik)
    }

    private val _uiStatePesmaPredlozi = MutableStateFlow(PesmaPredlozi())
    val uiStatePesmaPredlozi: StateFlow<PesmaPredlozi> = _uiStatePesmaPredlozi

    fun sacuvajPesmaPredlozi(id:Int,pesma:String,izvodjac: String,zanr: String,korisnik: String){
        _uiStatePesmaPredlozi.value= PesmaPredlozi(id,pesma,izvodjac,zanr,korisnik)
    }

    private val _uiStatePredlog = MutableStateFlow(UiStatePredlog())
    val uiStatePredlog: StateFlow<UiStatePredlog> = _uiStatePredlog

    fun dodajZanrSaFajlom(zanr: String, izvodjac: String, nazivPesme: String, nepoznatiStihovi: String,
                          poznatiStihovi: String, nivo: String, audioFile: RequestBody
    ) {
        viewModelScope.launch {
            val audioPart = MultipartBody.Part.createFormData(
                name = "audio",
                filename = "$nazivPesme - $nivo.mp3",
                body = audioFile
            )
            _uiStatePredlog.value = _uiStatePredlog.value.copy(isRefreshing = true)
            try {
                // val request = DodajZanrRequest(zanr,izvodjac,nazivPesme,nepoznatiStihovi,poznatiStihovi,nivo,audioPart)
                val response = repository.dodajZanr(zanr, izvodjac, nazivPesme,
                    nepoznatiStihovi, poznatiStihovi, nivo,
                    audioPart)
                _uiStatePredlog.value = UiStatePredlog(predlog = response, isRefreshing = false)
            } catch (e: Exception) {
                _uiStatePredlog.value =
                    UiStatePredlog(predlog = null, isRefreshing = false, error = e.localizedMessage)
            }
        }
    }
    fun resetDodajZanr() {
        _uiStatePredlog.value = _uiStatePredlog.value.copy(predlog = null)
    }

}

data class UiStatePredloziIzv(
    val predloziIzvodjaca: List<PredloziIzvodjacaResponse> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)


data class UiStatePredloziPes(
    val predloziPesama: List<PredloziPesamaResponse> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class IzvodjacPredlozi(
    val id:Int?=null,
    val izvodjac: String?=null,
    val zanr: String?=null,
    val korisnik:String?=null
)

data class PesmaPredlozi(
    val id:Int?=null,
    val pesma:String?=null,
    val izvodjac: String?=null,
    val zanr: String?=null,
    val korisnik:String?=null
)

data class UiStatePredlog(
    val predlog: DodajZanrResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)