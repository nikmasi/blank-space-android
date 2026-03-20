package com.example.blankspace.data

import com.example.blankspace.data.retrofit.AdminApi
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
import jakarta.inject.Inject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi
) : AdminRepository {

    override suspend fun getKorisniciUklanjanje(): List<KorisniciResponse> = adminApi.getKorisniciUklanjanje()
    override suspend fun getStatistika(): StatistikaResponse = adminApi.getStatistika()
    override suspend fun getPesmePoIzvodjacima(): List<PesmePoIzvodjacimaResponse> = adminApi.getPesmePoIzvodjacima()
    override suspend fun getStihoviPoPesmama(): List<StihoviPoPesmamaResponse> = adminApi.getStihoviPoPesmama()

    override suspend fun uklanjanjeKorisnika(request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse = adminApi.uklanjanjeKorisnika(request)
    override suspend fun uklanjanjeZanra(request: UklanjanjeZanraRequest): UklanjanjeZanraResponse = adminApi.uklanjanjeZanra(request)
    override suspend fun uklanjanjeIzvodjaca(request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse = adminApi.uklanjanjeIzvodjaca(request)
    override suspend fun uklanjanjePesme(request: UklanjanjePesmeRequest): UklanjanjePesmeResponse = adminApi.uklanjanjePesme(request)
    override suspend fun getIzvodjaciZanra(request: Zanr): List<IzvodjaciZanra> = adminApi.getIzvodjaciZanra(request)
    override suspend fun getPesmeIzvodjaca(request: Izvodjac): List<PesmeIzvodjaca> = adminApi.getPesmeIzvodjaca(request)
    override suspend fun getPregledKorisnik(requst: KorisnikPregledRequest): KorisnikPregledResponse = adminApi.getPregledKorisnik(requst)
    override suspend fun getPredloziIzvodjaca(): List<PredloziIzvodjacaResponse> = adminApi.getPredloziIzvodjaca()
    override suspend fun odbijPredlogIzvodjaca(request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse> = adminApi.odbijPredlogIzvodjaca(request)
    override suspend fun provera_da_li_postoji(request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse = adminApi.provera_da_li_postoji(request)
    override suspend fun dodajZanr( zanr: String,
                                    izvodjac: String,
                                    nazivPesme: String,
                                    nepoznatiStihovi: String,
                                    poznatiStihovi: String,
                                    nivo: String,
                                    zvuk: MultipartBody.Part): DodajZanrResponse = adminApi.dodajZanr(
        zanr = zanr.toRequestBody("text/plain".toMediaType()),
        izvodjac = izvodjac.toRequestBody("text/plain".toMediaType()),
        nazivPesme = nazivPesme.toRequestBody("text/plain".toMediaType()),
        nepoznatiStihovi = nepoznatiStihovi.toRequestBody("text/plain".toMediaType()),
        poznatiStihovi = poznatiStihovi.toRequestBody("text/plain".toMediaType()),
        nivo = nivo.toRequestBody("text/plain".toMediaType()),
        zvuk = zvuk
    )

}