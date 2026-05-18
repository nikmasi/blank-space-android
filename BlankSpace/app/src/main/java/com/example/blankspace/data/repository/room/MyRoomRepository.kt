package com.example.blankspace.data.repository.room

import android.util.Log
import androidx.work.PeriodicWorkRequest
import com.example.blankspace.data.retrofit.api.Api
import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.music.Pesma
import com.example.blankspace.data.retrofit.data.music.Stih
import com.example.blankspace.data.retrofit.data.music.Zanr
import com.example.blankspace.data.room.RoomDao
import com.example.blankspace.data.room.entity.IzvodjacEntity
import com.example.blankspace.data.room.entity.PesmaEntity
import com.example.blankspace.data.room.entity.StihoviEntity
import com.example.blankspace.data.room.entity.ZanrEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRoomRepository @Inject constructor(
    private val roomDao: RoomDao, private val api: Api
) {

    val allZanrovi = roomDao.getZanrovi()
    val allIzvodjaci = roomDao.getIzvodjaci()
    val allPesme = roomDao.getPesme()
    val allStihov = roomDao.getStihovi()
    val allSobe = roomDao.getSobe()

    suspend fun insert(zanr: ZanrEntity) { roomDao.insert(zanr) }

    fun Zanr.toEntity(): ZanrEntity {
        return ZanrEntity(id = this.id, naziv = this.naziv)
    }

    fun List<Zanr>.toEntityList(): List<ZanrEntity> {
        return this.map { it.toEntity() }
    }

    suspend fun fetchZanroviFromApi() {
        val response = api.getZanrovi()
        val entities = response.toEntityList()
        roomDao.deleteAll()
        roomDao.insertAll(entities)
    }

    fun Izvodjac.toIzvodjacEntity(): IzvodjacEntity {
        return IzvodjacEntity(id = this.id, ime = this.ime, zanId = this.zan)
    }

    fun List<Izvodjac>.toIzvodjacEntityList(): List<IzvodjacEntity> {
        return this.map { it.toIzvodjacEntity() }
    }

    suspend fun fetchIzvodjaciFromApi() {
        try {
            val response = api.getIzvodjaci()
            val entities = response.toIzvodjacEntityList()
            roomDao.deleteAllIzvodjac()
            roomDao.insertAllIzvodjac(entities)
        } catch (e: Exception) { }
    }


    fun Pesma.toPesmaEntity(): PesmaEntity {
        return PesmaEntity(id = this.id, naziv = this.naziv, izvId = this.izv)
    }

    fun List<Pesma>.toPesmaEntityList(): List<PesmaEntity> {
        return this.map { it.toPesmaEntity() }
    }


    fun Stih.toStihEntity(): StihoviEntity {
        return StihoviEntity(
            id = this.id, nivo = this.nivo, poznatTekst = this.poznat_tekst,
            nepoznatTekst = this.nepoznat_tekst, zvuk = this.zvuk_url, pesId = this.pes,
        )
    }

    fun List<Stih>.toStihEntityList(): List<StihoviEntity> {
        return this.map { it.toStihEntity() }
    }


    suspend fun fetchPesmaFromApi() {
        try {
            val response = api.getPesme()
            val entities = response.toPesmaEntityList()
            roomDao.deleteAllPesme()
            roomDao.insertAllPesme(entities)
        } catch (e: Exception) { }
    }

    suspend fun fetchStihoviFromApi() {
        try {
            val response = api.getStihovi()
            val entities = response.toStihEntityList()
            Log.d("STIH STIH", entities.toString())
            roomDao.deleteAllStihovi()
            roomDao.insertAllStihovi(entities)
        } catch (e: Exception) { }
    }

    suspend fun getStihoviPoTezini(tezina:String):List<StihoviEntity>{
        return roomDao.getStihoviPoTezini(tezina)
    }

    suspend fun getPesmaPoId(id:Int): PesmaEntity {
        return roomDao.getPesmaPoId(id)
    }

    suspend fun getIzvodjacPoId(id:Int): IzvodjacEntity {
        return roomDao.getIzvodjacPoId(id)
    }

    suspend fun getStihoviPoTeziniIZanrovima(tezina: String,zanrovi:List<Int>):List<StihoviEntity>{
        return roomDao.getStihoviPoTeziniIZanrovima(tezina,zanrovi)
    }

    suspend fun syncAllData() {
        fetchZanroviFromApi()
        fetchIzvodjaciFromApi()
        fetchPesmaFromApi()
        fetchStihoviFromApi()
    }

}