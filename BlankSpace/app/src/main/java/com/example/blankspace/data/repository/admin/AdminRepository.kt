package com.example.blankspace.data.repository.admin

import com.example.blankspace.data.retrofit.data.DodajZanrResponse
import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.IzvodjaciZanra
import com.example.blankspace.data.retrofit.data.user.KorisniciResponse
import com.example.blankspace.data.retrofit.data.user.KorisnikPregledRequest
import com.example.blankspace.data.retrofit.data.user.KorisnikPregledResponse
import com.example.blankspace.data.retrofit.data.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.data.PesmePoIzvodjacimaResponse
import com.example.blankspace.data.retrofit.data.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.data.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.data.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.data.ProveraDaLiPostojiResponse
import com.example.blankspace.data.retrofit.data.stats.StatistikaResponse
import com.example.blankspace.data.retrofit.data.StihoviPoPesmamaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.data.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.data.music.Zanr
import okhttp3.MultipartBody

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