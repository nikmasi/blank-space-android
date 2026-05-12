package com.example.blankspace.data.repository.content

import com.example.blankspace.data.retrofit.api.ContentApi
import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.IzvodjaciZanra
import com.example.blankspace.data.retrofit.data.user.MojProfilRequest
import com.example.blankspace.data.retrofit.data.user.MojProfilResponse
import com.example.blankspace.data.retrofit.data.RangListaResponse
import com.example.blankspace.data.retrofit.data.music.Zanr
import com.example.blankspace.data.retrofit.data.music.ZanrNazivRequest
import jakarta.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentApi: ContentApi
) : ContentRepository {
    override suspend fun getZanrovi(): List<Zanr> = contentApi.getZanrovi()
    override suspend fun getIzvodjaciZanra(request: Zanr): List<IzvodjaciZanra> = contentApi.getIzvodjaciZanra(request)
    override suspend fun dohvati_izvodjace_zanra(request: ZanrNazivRequest): List<IzvodjaciZanra> = contentApi.dohvati_izvodjace_zanra(request)
    override suspend fun getIzvodjaci(): List<Izvodjac> = contentApi.getIzvodjaci()
    override suspend fun getMojProfilData(request: MojProfilRequest): MojProfilResponse = contentApi.getMojProfilData(request)
    override suspend fun getRangLista(): List<RangListaResponse> = contentApi.getRangLista()
}