package com.example.blankspace.data

import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.MojProfilRequest
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import com.example.blankspace.data.retrofit.models.RangListaResponse
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
import retrofit2.http.Body

interface ContentRepository{
    suspend fun getZanrovi(): List<Zanr>

    suspend fun getIzvodjaciZanra(request: Zanr): List<IzvodjaciZanra>

    suspend fun dohvati_izvodjace_zanra(request: ZanrNazivRequest):List<IzvodjaciZanra>

    suspend fun getIzvodjaci(): List<Izvodjac>

    suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse

    suspend fun getRangLista(): List<RangListaResponse>


}