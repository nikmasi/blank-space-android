package com.example.blankspace.data.repository.suggestion

import com.example.blankspace.data.retrofit.data.DodajZanrResponse
import com.example.blankspace.data.retrofit.data.PredlaganjeIzvodjacaRequset
import com.example.blankspace.data.retrofit.data.PredlaganjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.data.PredlaganjePesmeRequset
import com.example.blankspace.data.retrofit.data.PredlaganjePesmeResponse
import com.example.blankspace.data.retrofit.data.PredlaganjePretraziRequest
import com.example.blankspace.data.retrofit.data.PredlaganjePretraziResponse
import com.example.blankspace.data.retrofit.data.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.data.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.data.PredloziPesamaOdbijRequest
import com.example.blankspace.data.retrofit.data.PredloziPesamaResponse
import com.example.blankspace.data.retrofit.data.scraper.WebScrapperRequest
import com.example.blankspace.data.retrofit.data.scraper.WebScrapperResponse
import okhttp3.MultipartBody

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