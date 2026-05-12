package com.example.blankspace.data.repository.content

import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.IzvodjaciZanra
import com.example.blankspace.data.retrofit.data.user.MojProfilRequest
import com.example.blankspace.data.retrofit.data.user.MojProfilResponse
import com.example.blankspace.data.retrofit.data.RangListaResponse
import com.example.blankspace.data.retrofit.data.music.Zanr
import com.example.blankspace.data.retrofit.data.music.ZanrNazivRequest
import retrofit2.http.Body

interface ContentRepository{
    suspend fun getZanrovi(): List<Zanr>

    suspend fun getIzvodjaciZanra(request: Zanr): List<IzvodjaciZanra>

    suspend fun dohvati_izvodjace_zanra(request: ZanrNazivRequest):List<IzvodjaciZanra>

    suspend fun getIzvodjaci(): List<Izvodjac>

    suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse

    suspend fun getRangLista(): List<RangListaResponse>


}