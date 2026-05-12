package com.example.blankspace.data.retrofit.api

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
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SuggestionApi{
    @POST("predlaganje_izvodjaca_android/")
    suspend fun predlaganje_izvodjaca(@Body predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset):
            PredlaganjeIzvodjacaResponse

    @POST("predlaganje_pretrazi_android/")
    suspend fun predlaganje_pretrazi(@Body predlaganjePretraziRequest: PredlaganjePretraziRequest):
            PredlaganjePretraziResponse

    @POST("predlaganje_pesme_android/")
    suspend fun predlaganje_pesme(@Body predlaganjePesmeRequset: PredlaganjePesmeRequset):
            PredlaganjePesmeResponse

    @POST("web_scrapper_android/")
    suspend fun web_scrapper(@Body request: WebScrapperRequest): List<WebScrapperResponse>

    @GET("predlozi_izvodjaca_android/")
    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>

    @POST("predlozi_izvodjaca_odbij_android/")
    suspend fun odbijPredlogIzvodjaca(@Body request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>

    @GET("predlozi_pesme_android/")
    suspend fun getPredloziPesme():List<PredloziPesamaResponse>

    @POST("predlozi_pesme_odbij_android/")
    suspend fun odbijPredlogPesme(@Body request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>

    @Multipart
    @POST("dodaj_zanr_android/")
    suspend fun dodajZanr(@Part("zanr") zanr: RequestBody,
                          @Part("izvodjac") izvodjac: RequestBody,
                          @Part("naziv_pesme") nazivPesme: RequestBody,
                          @Part("nepoznati_stihovi") nepoznatiStihovi: RequestBody,
                          @Part("poznati_stihovi") poznatiStihovi: RequestBody,
                          @Part("nivo") nivo: RequestBody,
                          @Part zvuk: MultipartBody.Part): DodajZanrResponse
}