package com.example.blankspace.viewModels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.AudioRequest
import com.example.blankspace.data.retrofit.models.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.models.CekanjeRezultataResponse
import com.example.blankspace.data.retrofit.models.DuelRequest
import com.example.blankspace.data.retrofit.models.DuelResponse
import com.example.blankspace.data.retrofit.models.GenerisiSifruRequest
import com.example.blankspace.data.retrofit.models.GenerisiSifruResponse
import com.example.blankspace.data.retrofit.models.KrajDuelaRequest
import com.example.blankspace.data.retrofit.models.KrajDuelaResponse
import com.example.blankspace.data.retrofit.models.ProveriSifruRequest
import com.example.blankspace.data.retrofit.models.ProveriSifruResponse
import com.example.blankspace.data.retrofit.models.StigaoIgracRequest
import com.example.blankspace.data.retrofit.models.StigaoIgracResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DuelViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateD())
    val uiState: StateFlow<UiStateD> = _uiState

    private val _isAudioPlaying = MutableStateFlow(false)
    private var mediaPlayer:MediaPlayer?=null

    private val _uiStateSifSobe = MutableStateFlow(UiStateSifSobe())
    val uiStateSifSobe: StateFlow<UiStateSifSobe> = _uiStateSifSobe

    private val _uiStateProveriSifru = MutableStateFlow(UiStateProveriSifru())
    val uiStateProveriSifru: StateFlow<UiStateProveriSifru> = _uiStateProveriSifru

    private val _uiStateStigaoIgrac = MutableStateFlow(UiStateStigaoIgrac())
    val uiStateStigaoIgrac: StateFlow<UiStateStigaoIgrac> = _uiStateStigaoIgrac

    fun generisiSifru(korisnickoIme:String) = viewModelScope.launch {
        _uiStateSifSobe.value = _uiStateSifSobe.value.copy(isRefreshing = true)
        try {
            val request = GenerisiSifruRequest(korisnickoIme)
            val response = repository.generisiSifru(request)
            _uiStateSifSobe.value = UiStateSifSobe(sifraResponse = response, isRefreshing = false)

            response.sifra?.let { upisiSifruSobe(it) }

        }catch (e: Exception) {
            _uiStateSifSobe.value = UiStateSifSobe(sifraResponse = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun proveriSifru(korisnickoIme: String, sifra:Int)= viewModelScope.launch {
        _uiStateProveriSifru.value = _uiStateProveriSifru.value.copy(isRefreshing = true)
        try {
            val request = ProveriSifruRequest(korisnickoIme,sifra)
            val response = repository.proveriSifruSobe(request)
            _uiStateProveriSifru.value = UiStateProveriSifru(proveriSifru  = response, isRefreshing = false)
        }catch (e: Exception) {
            _uiStateProveriSifru.value = UiStateProveriSifru(proveriSifru = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun dodeli(uiStateProveriSifru: UiStateProveriSifru,sifra: Int){
        _uiStateSifSobe.value = _uiStateSifSobe.value.copy(isRefreshing = true)
        _uiStateSifSobe.value = UiStateSifSobe(sifraResponse =
        uiStateProveriSifru.proveriSifru?.let
        { GenerisiSifruResponse(sifra, it.stihovi) }, isRefreshing = false)
    }

    fun stigaoIgrac(sifra: Int)= viewModelScope.launch {
        _uiStateStigaoIgrac.value = _uiStateStigaoIgrac.value.copy(isRefreshing = true)
        try {
            val request = StigaoIgracRequest(sifra)
            val response = repository.stigaoIgrac(request)
            _uiStateStigaoIgrac.value = UiStateStigaoIgrac(stigaoIgrac = response, isRefreshing = false)
        }catch (e: Exception) {
            _uiStateStigaoIgrac.value = UiStateStigaoIgrac(stigaoIgrac = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun dodaj(vrednost:Int){
        val updatedPoeni = (_uiState.value.duel?.rundePoeni ?: emptyList()) + vrednost
        _uiState.value.duel?.rundePoeni=updatedPoeni
    }

    fun fetchDuel(runda:Int,poeni:Int,stihovi:String,rundaPoeni:List<Int>,context: Context) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val request = DuelRequest(runda,poeni,stihovi,rundaPoeni)
            val response = repository.duel(request)

            _uiState.value = UiStateD(duel = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateD(duel = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    // Funkcija za preuzimanje MP3 fajla
    fun downloadAudio(url: String,context: Context) {
        viewModelScope.launch {
            try {
                val audioReq =AudioRequest(url)
                val respose =repository.getAudio(audioReq)
                playAudioFromUrl(respose.audio_url)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Greška pri preuzimanju MP3 fajla: ${e.localizedMessage}")
            }
        }
    }
    private fun playAudioFromUrl(url: String) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)  // Postavljamo URL kao izvor
                prepare()            // Pripremamo mediaPlayer
                start()              // Počinjemo reprodukciju
            }
            // Opcionalno, postavimo listener za završetak reprodukcije
            mediaPlayer?.setOnCompletionListener {
                Log.d("Audio", "Audio playback completed.")
            }

        } catch (e: Exception) {
            Log.e("Audio", "Error while playing audio: ${e.localizedMessage}")
        }
    }
    fun stopAudio() {
        mediaPlayer?.let {
            try {
                it.stop()  // Zaustavlja reprodukciju
                it.release()  // Oslobađa resurse
                _isAudioPlaying.value = false  // Postavljamo stanje da je audio zaustavljen
                Log.d("Audio", "Audio playback stopped.")
            } catch (e: Exception) {
                Log.e("Audio", "Error while stopping audio: ${e.localizedMessage}")
            }
        }
    }

    private val _uiStateCekanjeRezultata = MutableStateFlow(UiStateCekanjeRezultata())
    val uiStateCekanjeRezultata: StateFlow<UiStateCekanjeRezultata> = _uiStateCekanjeRezultata

    fun fetchCekanjeRezultata(poeni:Int,soba:Int,rundaPoeni:List<Int>,redniBroj: Int) = viewModelScope.launch {
        _uiStateCekanjeRezultata.value = _uiStateCekanjeRezultata.value.copy(isRefreshing = true)
        try {
            val request = CekanjeRezultataRequst(rundaPoeni,poeni,soba,redniBroj)
            val response = repository.cekanjeRezultata(request)

            _uiStateCekanjeRezultata.value = UiStateCekanjeRezultata(cekanjeRezultata = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateCekanjeRezultata.value =
                UiStateCekanjeRezultata(cekanjeRezultata = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    private val _sifraSobe = MutableStateFlow(SifraSobe())
    val sifraSobe: StateFlow<SifraSobe> = _sifraSobe

    fun upisiSifruSobe(sifra: Int){
        _sifraSobe.value =
            SifraSobe(sifra = sifra)
    }

    private val _redniBroj = MutableStateFlow(RedniBroj())
    val redniBroj: StateFlow<RedniBroj> = _redniBroj

    fun upisiRedniBroj(redniBroj: Int){
        _redniBroj.value =
            RedniBroj(redniBroj = redniBroj)
    }

    private val _uiStateKrajDuela = MutableStateFlow(UiStateKrajDuela(emptyList()))
    val uiStateKrajDuela: StateFlow<UiStateKrajDuela> = _uiStateKrajDuela

    fun fetchKrajDuela(poeni:Int,soba:Int,rundaPoeni:List<Int>,redniBroj: Int,upisuj:String) = viewModelScope.launch {
        _uiStateKrajDuela.value = _uiStateKrajDuela.value.copy(isRefreshing = true)
        try {
            val request = KrajDuelaRequest(rundaPoeni,poeni*10,soba,redniBroj,upisuj)
            val response = repository.krajDuela(request)
            Log.d("SIFF333",response.toString())

            _uiStateKrajDuela.value = UiStateKrajDuela(poeni_runde = response.poeni_runde, krajDuela = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateKrajDuela.value =
                UiStateKrajDuela(poeni_runde = emptyList(),krajDuela = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

}

data class UiStateSifSobe(
    val sifraResponse: GenerisiSifruResponse?=null,
    val isRefreshing: Boolean=false,
    val error: String? = null
)

data class UiStateProveriSifru(
    val proveriSifru: ProveriSifruResponse?=null,
    val isRefreshing: Boolean=false,
    val error: String? = null
)

data class UiStateStigaoIgrac(
    val stigaoIgrac: StigaoIgracResponse?=null,
    val isRefreshing: Boolean=false,
    val error: String? = null
)


data class UiStateD(
    val duel: DuelResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateCekanjeRezultata(
    val cekanjeRezultata: CekanjeRezultataResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class SifraSobe(
    val sifra: Int=-1
)

data class RedniBroj(
    val redniBroj:Int=-1
)

data class UiStateKrajDuela(
    val poeni_runde: List<List<Int>>,
    val krajDuela: KrajDuelaResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)