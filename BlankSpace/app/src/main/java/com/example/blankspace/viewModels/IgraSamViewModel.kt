package com.example.blankspace.viewModels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.GameRepository
import com.example.blankspace.data.retrofit.BASE_URL
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
    private val gameRepository: GameRepository
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
            val response = gameRepository.getIgraSamData(request)
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
            val response = gameRepository.krajIgre(request)
            _uiStateKI.value = UiStateKrajIgre(krajIgre  = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateKI.value = UiStateKrajIgre(krajIgre = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun postaviListu(lista:List<Int>){
        _IgraSamLista.value=IgraSamLista(igraSamLista = lista)
    }

    private var mediaPlayer: MediaPlayer? = null

    fun downloadAudio(url: String, context: Context) {
        viewModelScope.launch {
            try {
                stopAudio()

                val audioReq = AudioRequest(url)
                val response = gameRepository.getAudio(audioReq)

                val siroviUrl = response.audio_url
                val popravljenUrl = fixAndEncodeUrl(siroviUrl)

                Log.d("AudioAudio", "Originalni URL: $siroviUrl")
                Log.d("AudioAudio", "Popravljeni URL: $popravljenUrl")

                playAudioFromUrl(popravljenUrl)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Greška pri preuzimanju MP3 fajla: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun fixAndEncodeUrl(url: String): String {
        val mojServerIp = BASE_URL
        val basePattern = Regex("http://[^/]+:8000/")
        val urlSaNovimIp = url.replace(basePattern, "$mojServerIp")
        return urlSaNovimIp.replace(" ", "%20")
    }

    private fun playAudioFromUrl(url: String) {
        try {

            stopAudio()

            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)
                setDataSource(url)
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    _isAudioPlaying.value = false
                    Log.d("AudioAudio", "Audio playback completed.")
                }
                setOnErrorListener { _, what, extra ->
                    Log.e("AudioAudio", "MediaPlayer error: what=$what, extra=$extra")
                    stopAudio()
                    true
                }
                prepareAsync()
            }

            _isAudioPlaying.value = true

        } catch (e: Exception) {
            Log.e("AudioAudio", "Error while playing audio: ${e.localizedMessage}")
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
            Log.e("AudioAudio", "Error while stopping audio: ${e.localizedMessage}")
        } finally {
            mediaPlayer = null
            _isAudioPlaying.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopAudio()
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