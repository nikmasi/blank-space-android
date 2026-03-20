package com.example.blankspace.data.retrofit

import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.MojProfilRequest
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import com.example.blankspace.data.retrofit.models.RangListaResponse
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
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