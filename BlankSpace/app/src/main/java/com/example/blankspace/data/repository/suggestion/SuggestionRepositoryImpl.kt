package com.example.blankspace.data.repository.suggestion

import com.example.blankspace.data.retrofit.api.SuggestionApi
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
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class SuggestionRepositoryImpl  @Inject constructor(
    private val suggestionApi: SuggestionApi
): SuggestionRepository {
    override suspend fun predlaganje_izvodjaca(predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset): PredlaganjeIzvodjacaResponse
        = suggestionApi.predlaganje_izvodjaca(predlaganjeIzvodjacaRequset)

    override suspend fun predlaganje_pretrazi(predlaganjePretraziRequest: PredlaganjePretraziRequest): PredlaganjePretraziResponse
        = suggestionApi.predlaganje_pretrazi(predlaganjePretraziRequest)

    override suspend fun predlaganje_pesme(predlaganjePesmeRequset: PredlaganjePesmeRequset): PredlaganjePesmeResponse
        = suggestionApi.predlaganje_pesme(predlaganjePesmeRequset)

    override suspend fun web_scrapper(request: WebScrapperRequest): List<WebScrapperResponse>
        = suggestionApi.web_scrapper(request)

    override suspend fun getPredloziIzvodjaca(): List<PredloziIzvodjacaResponse>
        = suggestionApi.getPredloziIzvodjaca()

    override suspend fun odbijPredlogIzvodjaca(request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>
        = suggestionApi.odbijPredlogIzvodjaca(request)

    override suspend fun getPredloziPesme(): List<PredloziPesamaResponse>
        = suggestionApi.getPredloziPesme()

    override suspend fun odbijPredlogPesme(request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>
        = suggestionApi.odbijPredlogPesme(request)

    override suspend fun dodajZanr( zanr: String,
                                    izvodjac: String,
                                    nazivPesme: String,
                                    nepoznatiStihovi: String,
                                    poznatiStihovi: String,
                                    nivo: String,
                                    zvuk: MultipartBody.Part): DodajZanrResponse = suggestionApi.dodajZanr(
        zanr = zanr.toRequestBody("text/plain".toMediaType()),
        izvodjac = izvodjac.toRequestBody("text/plain".toMediaType()),
        nazivPesme = nazivPesme.toRequestBody("text/plain".toMediaType()),
        nepoznatiStihovi = nepoznatiStihovi.toRequestBody("text/plain".toMediaType()),
        poznatiStihovi = poznatiStihovi.toRequestBody("text/plain".toMediaType()),
        nivo = nivo.toRequestBody("text/plain".toMediaType()),
        zvuk = zvuk
    )

}