package com.example.blankspace.data

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

interface AdminRepository{
    suspend fun getKorisniciUklanjanje():List<KorisniciResponse>

    suspend fun getStatistika():StatistikaResponse

    suspend fun getPesmePoIzvodjacima():List<PesmePoIzvodjacimaResponse>

    suspend fun getStihoviPoPesmama():List<StihoviPoPesmamaResponse>

    suspend fun uklanjanjeKorisnika(request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse
    suspend fun uklanjanjeZanra(request: UklanjanjeZanraRequest): UklanjanjeZanraResponse
    suspend fun uklanjanjeIzvodjaca(request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse
    suspend fun uklanjanjePesme(request: UklanjanjePesmeRequest): UklanjanjePesmeResponse

    suspend fun getIzvodjaciZanra(request: Zanr): List<IzvodjaciZanra>

    suspend fun getPesmeIzvodjaca(request: Izvodjac): List<PesmeIzvodjaca>

    suspend fun getPregledKorisnik(requst: KorisnikPregledRequest):KorisnikPregledResponse

    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>

    suspend fun odbijPredlogIzvodjaca(request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>

    suspend fun provera_da_li_postoji(request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse

    suspend fun dodajZanr( zanr: String,
                           izvodjac: String,
                           nazivPesme: String,
                           nepoznatiStihovi: String,
                           poznatiStihovi: String,
                           nivo: String,
                           zvuk: MultipartBody.Part): DodajZanrResponse
}