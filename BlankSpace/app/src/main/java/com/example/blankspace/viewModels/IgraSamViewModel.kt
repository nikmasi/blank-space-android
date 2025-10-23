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
    //private var mediaPlayer:MediaPlayer?=null

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
    private var mediaPlayer: MediaPlayer? = null

    // Funkcija za preuzimanje MP3 fajla i puštanje
    fun downloadAudio(url: String, context: Context) {
        viewModelScope.launch {
            try {
                // Uvek prvo zaustavi prethodnu pesmu
                stopAudio()

                val audioReq = AudioRequest(url)
                val response = repository.getAudio(audioReq)

                // Kada dobiješ novi URL, pusti ga
                playAudioFromUrl(response.audio_url)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Greška pri preuzimanju MP3 fajla: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun playAudioFromUrl(url: String) {
        try {
            // Uveri se da nema starih MediaPlayer instanci
            stopAudio()

            mediaPlayer = MediaPlayer().apply {
                setDataSource(url)
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    _isAudioPlaying.value = false
                    Log.d("Audio", "Audio playback completed.")
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("Audio", "MediaPlayer error: what=$what, extra=$extra")
                    stopAudio()
                    true
                }
                prepareAsync() // ✅ koristi async pripremu
            }

            _isAudioPlaying.value = true

        } catch (e: Exception) {
            Log.e("Audio", "Error while playing audio: ${e.localizedMessage}")
        }
    }

    fun stopAudio() {
        try {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    player.stop()
                }
                player.reset()
                player.release()
            }
        } catch (e: Exception) {
            Log.e("Audio", "Error while stopping audio: ${e.localizedMessage}")
        } finally {
            mediaPlayer = null
            _isAudioPlaying.value = false
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