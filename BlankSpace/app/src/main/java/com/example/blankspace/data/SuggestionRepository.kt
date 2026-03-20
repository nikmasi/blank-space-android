package com.example.blankspace.data

import com.example.blankspace.data.retrofit.models.DodajZanrResponse
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaRequset
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeRequset
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeResponse
import com.example.blankspace.data.retrofit.models.PredlaganjePretraziRequest
import com.example.blankspace.data.retrofit.models.PredlaganjePretraziResponse
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.PredloziPesamaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziPesamaResponse
import com.example.blankspace.data.retrofit.models.WebScrapperRequest
import com.example.blankspace.data.retrofit.models.WebScrapperResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SuggestionRepository {

    suspend fun predlaganje_izvodjaca(predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset):
            PredlaganjeIzvodjacaResponse

    suspend fun predlaganje_pretrazi(predlaganjePretraziRequest: PredlaganjePretraziRequest):
            PredlaganjePretraziResponse

    suspend fun predlaganje_pesme(predlaganjePesmeRequset: PredlaganjePesmeRequset):
            PredlaganjePesmeResponse

    suspend fun web_scrapper(request: WebScrapperRequest): List<WebScrapperResponse>

    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>
    suspend fun odbijPredlogIzvodjaca(request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>
    suspend fun getPredloziPesme():List<PredloziPesamaResponse>
    suspend fun odbijPredlogPesme(request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>

    suspend fun dodajZanr( zanr: String,
                           izvodjac: String,
                           nazivPesme: String,
                           nepoznatiStihovi: String,
                           poznatiStihovi: String,
                           nivo: String,
                           zvuk: MultipartBody.Part): DodajZanrResponse
}