package com.example.blankspace.data.retrofit.api

import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.IzvodjaciZanra
import com.example.blankspace.data.retrofit.data.user.MojProfilRequest
import com.example.blankspace.data.retrofit.data.user.MojProfilResponse
import com.example.blankspace.data.retrofit.data.RangListaResponse
import com.example.blankspace.data.retrofit.data.music.Zanr
import com.example.blankspace.data.retrofit.data.music.ZanrNazivRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContentApi {
    @GET("zanrovi")
    suspend fun getZanrovi(): List<Zanr>

    @POST("izvodjaci_zanra_andoid/")
    suspend fun getIzvodjaciZanra(@Body request: Zanr): List<IzvodjaciZanra>

    @POST("dohvati_izvodjace_zanra_android/")
    suspend fun dohvati_izvodjace_zanra(@Body request: ZanrNazivRequest):List<IzvodjaciZanra>

    @GET("izvodjaci_andoid")
    suspend fun getIzvodjaci(): List<Izvodjac>

    @POST("pregled_profila_andoid/")
    suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse

    @GET("rang_lista_andoid")
    suspend fun getRangLista(): List<RangListaResponse>
}