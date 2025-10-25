package com.example.blankspace.viewModels

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blankspace.data.MyRoomRepository
import com.example.blankspace.data.retrofit.models.IgraOfflineData
import com.example.blankspace.data.retrofit.models.IgraSamRequest
import com.example.blankspace.data.retrofit.models.IgraSamResponse
import com.example.blankspace.data.retrofit.models.KrajIgreRequest
import com.example.blankspace.data.retrofit.models.KrajIgreResponse
import com.example.blankspace.data.room.RoomDao
import com.example.blankspace.data.room.ZanrEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val myRoomRepository: MyRoomRepository
) : ViewModel() {

    val allZanrovi = myRoomRepository.allZanrovi

    fun insert(word: ZanrEntity) = viewModelScope.launch {
        myRoomRepository.insert(word)
    }

    fun loadZanrovi() {
        viewModelScope.launch {
            val fromApi = myRoomRepository.fetchZanroviFromApi()
        }
    }

    fun loadIzvodjaci() {
        viewModelScope.launch {
            myRoomRepository.fetchIzvodjaciFromApi()
        }
    }

    fun loadPesme() {
        viewModelScope.launch {
            myRoomRepository.fetchPesmaFromApi()
        }
    }
    fun loadStihovi() {
        viewModelScope.launch {
            myRoomRepository.fetchStihoviFromApi()
        }
    }


    private val _uiState = MutableStateFlow(UiStateIgraOffline())
    val uiState: StateFlow<UiStateIgraOffline> = _uiState

    private val _uiStateKI = MutableStateFlow(UiStateOfflineKrajIgre())
    val uiStateKI: StateFlow<UiStateOfflineKrajIgre> = _uiStateKI

    private fun mapTezinaToNivo(tezina: String): String {
        return when (tezina.lowercase()) {
            "lako", "easy" -> "E"
            "normalno", "medium", "srednje" -> "N"
            "tesko", "hard" -> "H"
            else -> "N" // podrazumevano
        }
    }



    private val _IgraOfflineLista= MutableStateFlow(IgraOfflineLista())
    val IgraOfflineLista: StateFlow<IgraOfflineLista> = _IgraOfflineLista

    fun fetchIgraOfflineData(zanrovi: List<String>, tezina: String,runda:Int,poeni:Int,listaBilo:List<Int>,context: Context) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        try {

            // Prebaci žanrove u int (ako su u string formatu)
            val intList = zanrovi.map { it.toInt() }

            // Filtriraj stihove po težini i žanru
            //val sviStihovi = myRoomRepository.getStihoviPoTezini(mapTezinaToNivo(tezina))
            //val filtrirani = sviStihovi.filter { it.pesId !in listaBilo }

            val sviStihovi = myRoomRepository.getStihoviPoTeziniIZanrovima(mapTezinaToNivo(tezina), intList)
            val filtrirani = sviStihovi.filter { it.pesId !in listaBilo }


            Log.d("Nasu filtrirani",filtrirani.toString())
            if (filtrirani.isEmpty()) {
                _uiState.value = UiStateIgraOffline(
                    igraoffline = null,
                    isRefreshing = false,
                    error = "Nema više dostupnih stihova"
                )
                return@launch
            }
            Log.d("Nasu fil",filtrirani.toString())

            // Nasumično izaberi jedan stih
            val randomStih = filtrirani.random()

            Log.d("Nasu",randomStih.toString())

            // Dohvati povezanu pesmu i izvođača
            val pesma = myRoomRepository.getPesmaPoId(randomStih.pesId)
            val izvodjac = myRoomRepository.getIzvodjacPoId(pesma.izvId)


            Log.d("Nasu pesma",pesma.toString())

            val poznatTekstCist = randomStih.poznatTekst.replace("<br>", "\n")
            val nepoznatTekstCist = randomStih.nepoznatTekst.replace("<br>", "\n")

            // Formiraj objekat za igru
            val response = IgraOfflineData(
                stihpoznat = listOf(poznatTekstCist),
                crtice = "_ ".repeat(randomStih.nepoznatTekst.length),
                tacno = nepoznatTekstCist,
                izvodjac = izvodjac.ime,
                zvuk = randomStih.zvuk,
                pesma = pesma.naziv,
                runda = runda,
                poeni = poeni,
                listaBilo = listaBilo + randomStih.pesId
            )

            // Postavi state
            _uiState.value = UiStateIgraOffline(
                igraoffline = response,
                isRefreshing = false
            )
            _uiState.value.igraoffline?.runda= _uiState.value.igraoffline?.runda?.plus(1)!!

        } catch (e: Exception) {
            _uiState.value = UiStateIgraOffline(
                igraoffline = null,
                isRefreshing = false,
                error = e.localizedMessage ?: "Greška pri dohvaćanju offline podataka"
            )
        }
    }

    fun fetchKrajIgre(korisnickoIme:String,poeni: Int)= viewModelScope.launch {
        _uiStateKI.value = _uiStateKI.value.copy(isRefreshing = true)
        try {
            val request = KrajIgreRequest(korisnickoIme, poeni)
            //val response = repository.krajIgre(request)
            //_uiStateKI.value = UiStateOfflineKrajIgre(krajOfflineIgre  = response, isRefreshing = false)
        } catch (e: Exception) {
            _uiStateKI.value = UiStateOfflineKrajIgre(krajOfflineIgre = null, isRefreshing = false, error = e.localizedMessage)
        }
    }

    fun postaviListu(lista:List<Int>){
        _IgraOfflineLista.value=IgraOfflineLista(igraOfflineLista = lista)
    }

}

data class IgraOfflineLista(
    var igraOfflineLista:List<Int>?= emptyList()
)

data class UiStateOfflineKrajIgre(
    val krajOfflineIgre: KrajIgreResponse?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

data class UiStateIgraOffline(
    val igraoffline: IgraOfflineData?=null,
    val isRefreshing: Boolean = false,
    val error: String? = null
)