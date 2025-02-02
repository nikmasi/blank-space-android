package com.example.blankspace.viewModels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.Repository
import com.example.blankspace.data.retrofit.models.AudioRequest
import com.example.blankspace.data.retrofit.models.IgraSamRequest
import com.example.blankspace.data.retrofit.models.IgraSamResponse
import com.example.blankspace.data.retrofit.models.KrajIgreRequest
import com.example.blankspace.data.retrofit.models.KrajIgreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IgraSamViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiStateI())
    val uiState: StateFlow<UiStateI> = _uiState

    private val _uiStateKI = MutableStateFlow(UiStateKrajIgre())
    val uiStateKI: StateFlow<UiStateKrajIgre> = _uiStateKI

    private val _isAudioPlaying = MutableStateFlow(false)
    private var mediaPlayer:MediaPlayer?=null

    private val _IgraSamLista= MutableStateFlow(IgraSamLista())
    val IgraSamLista:StateFlow<IgraSamLista> = _IgraSamLista

    fun fetchIgraSamData(zanrovi: List<String>, tezina: String,runda:Int,poeni:Int,listaBilo:List<Int>,context: Context) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {
            val intList = zanrovi.map { it.toInt() }
            val request = IgraSamRequest(intList, tezina,runda,poeni,listaBilo)
            val response = repository.getIgraSamData(request)
            postaviListu(response.listaBilo)

            _uiState.value = UiStateI(igrasam = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiState.value = UiStateI(igrasam = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun fetchKrajIgre(korisnickoIme:String,poeni: Int)= viewModelScope.launch {
        _uiStateKI.value = _uiStateKI.value.copy(isRefreshing = true)
        try {
            val request = KrajIgreRequest(korisnickoIme, poeni)
            val response = repository.krajIgre(request)
            _uiStateKI.value = UiStateKrajIgre(krajIgre  = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateKI.value = UiStateKrajIgre(krajIgre = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun postaviListu(lista:List<Int>){
        _IgraSamLista.value=IgraSamLista(igraSamLista = lista)
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
}

data class UiStateI(
    val igrasam:IgraSamResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class IgraSamLista(
    var igraSamLista:List<Int>?= emptyList()
)

data class UiStateKrajIgre(
    val krajIgre: KrajIgreResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)