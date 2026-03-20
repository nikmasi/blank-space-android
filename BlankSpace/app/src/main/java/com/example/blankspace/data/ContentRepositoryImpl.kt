package com.example.blankspace.data

import com.example.blankspace.data.retrofit.ContentApi
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.MojProfilRequest
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import com.example.blankspace.data.retrofit.models.RangListaResponse
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
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
