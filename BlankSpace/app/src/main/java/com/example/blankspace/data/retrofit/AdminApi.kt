package com.example.blankspace.data.retrofit

import com.example.blankspace.data.retrofit.models.DodajZanrResponse
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import com.example.blankspace.data.retrofit.models.KorisnikPregledRequest
import com.example.blankspace.data.retrofit.models.KorisnikPregledResponse
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.models.PesmePoIzvodjacimaResponse
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiResponse
import com.example.blankspace.data.retrofit.models.StatistikaResponse
import com.example.blankspace.data.retrofit.models.StihoviPoPesmamaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.models.Zanr
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AdminApi{
    @GET("uklanjanje_korisnika_android/")
    suspend fun getKorisniciUklanjanje():List<KorisniciResponse>

    @GET("statistika_android/")
    suspend fun getStatistika():StatistikaResponse

    @GET("pesme_po_izvodjacima_android/")
    suspend fun getPesmePoIzvodjacima():List<PesmePoIzvodjacimaResponse>

    @GET("stihovi_po_pesmama_android/")
    suspend fun getStihoviPoPesmama():List<StihoviPoPesmamaResponse>

    @POST("ukloni_korisnika_android/")
    suspend fun uklanjanjeKorisnika(@Body request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse

    @POST("ukloni_zanr_android/")
    suspend fun uklanjanjeZanra(@Body request: UklanjanjeZanraRequest): UklanjanjeZanraResponse

    @POST("ukloni_izvodjaca_android/")
    suspend fun uklanjanjeIzvodjaca(@Body request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse

    @POST("ukloni_pesmu_android/")
    suspend fun uklanjanjePesme(@Body request: UklanjanjePesmeRequest): UklanjanjePesmeResponse

    @POST("izvodjaci_zanra_andoid/")
    suspend fun getIzvodjaciZanra(@Body request: Zanr): List<IzvodjaciZanra>

    @POST("izvodjaci_pesme_andoid/")
    suspend fun getPesmeIzvodjaca(@Body request: Izvodjac): List<PesmeIzvodjaca>

    @POST("pregledKorisnik_android/")
    suspend fun getPregledKorisnik(@Body requst: KorisnikPregledRequest):KorisnikPregledResponse

    @GET("predlozi_izvodjaca_android/")
    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>

    @POST("predlozi_izvodjaca_odbij_android/")
    suspend fun odbijPredlogIzvodjaca(@Body request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>

    @POST("provera_da_li_postoji/")
    suspend fun provera_da_li_postoji(@Body request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse

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