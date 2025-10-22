package com.example.blankspace.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.DodajZanrRequest
import com.example.blankspace.data.retrofit.models.DodajZanrResponse
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class DodavanjeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStatePredloziIzv())
    val uiState: StateFlow<UiStatePredloziIzv> = _uiState

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


    private val _uiStateProveraPostojanja = MutableStateFlow(ProveraPostojanja())
    val uiStateProveraPostojanja: StateFlow<ProveraPostojanja> = _uiStateProveraPostojanja

    fun proveraPostojanja(vrednost:String,tip:String) = viewModelScope.launch {
        _uiStateProveraPostojanja.value = _uiStateProveraPostojanja.value.copy(isRefreshing = true)
        try {
            val request = ProveraDaLiPostojiRequest(vrednost,tip)
            val response = repository.provera_da_li_postoji(request)
            _uiStateProveraPostojanja.value = ProveraPostojanja(proveraDaLiPostoji = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateProveraPostojanja.value =
                ProveraPostojanja(proveraDaLiPostoji = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    private val _uiStateDodajZanr = MutableStateFlow(UiStateDodajZanr())
    val uiStateDodajZanr: StateFlow<UiStateDodajZanr> = _uiStateDodajZanr

    fun dodajZanr(zanr:String,izv:String,naz_pes:String,nep_stih:String,po_stih:String,nivo:String,zvuk:String)
    = viewModelScope.launch {
        /*_uiStateDodajZanr.value = _uiStateDodajZanr.value.copy(isRefreshing = true)
        try {
            val request = DodajZanrRequest(zanr,izv,naz_pes,nep_stih,po_stih,nivo,zvuk)
            val response = repository.dodajZanr(request)
            _uiStateDodajZanr.value = UiStateDodajZanr(dodajZanr = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateDodajZanr.value =
                UiStateDodajZanr(dodajZanr = null, isRefreshing = false, error = e.localizedMessage)
        }*/
    }

    fun dodajZanrSaFajlom(zanr: String, izvodjac: String, nazivPesme: String, nepoznatiStihovi: String,
        poznatiStihovi: String, nivo: String, audioFile: RequestBody
    ) {
        viewModelScope.launch {
            val audioPart = MultipartBody.Part.createFormData(
                name = "audio",
                filename = "$nazivPesme - $nivo.mp3",
                body = audioFile
            )

            Log.d("mp3 file", audioPart.toString())

            Log.d("mp3 file", audioPart.body.toString())

            _uiStateDodajZanr.value = _uiStateDodajZanr.value.copy(isRefreshing = true)
            try {
               // val request = DodajZanrRequest(zanr,izvodjac,nazivPesme,nepoznatiStihovi,poznatiStihovi,nivo,audioPart)
                val response = repository.dodajZanr(zanr, izvodjac, nazivPesme,
                    nepoznatiStihovi, poznatiStihovi, nivo,
                    audioPart)
                _uiStateDodajZanr.value = UiStateDodajZanr(dodajZanr = response, isRefreshing = false)
            } catch (e: Exception) {
                _uiStateDodajZanr.value =
                    UiStateDodajZanr(dodajZanr = null, isRefreshing = false, error = e.localizedMessage)
            }
        }
    }
    fun resetDodajZanr() {
        _uiStateDodajZanr.value = _uiStateDodajZanr.value.copy(dodajZanr = null)
    }

}

data class UiStatePredloziZanr(
    val predloziIzvodjaca: List<PredloziIzvodjacaResponse> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class ProveraPostojanja(
    val proveraDaLiPostoji: ProveraDaLiPostojiResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateDodajZanr(
    val dodajZanr: DodajZanrResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)