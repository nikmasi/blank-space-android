package com.example.blankspace.data.retrofit

import com.example.blankspace.data.retrofit.models.AudioRequest
import com.example.blankspace.data.retrofit.models.AudioResponse
import com.example.blankspace.data.retrofit.models.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.models.CekanjeRezultataResponse
import com.example.blankspace.data.retrofit.models.DodajZanrRequest
import com.example.blankspace.data.retrofit.models.DodajZanrResponse
import com.example.blankspace.data.retrofit.models.DuelRequest
import com.example.blankspace.data.retrofit.models.DuelResponse
import com.example.blankspace.data.retrofit.models.GenerisiSifruRequest
import com.example.blankspace.data.retrofit.models.GenerisiSifruResponse
import com.example.blankspace.data.retrofit.models.IgraSamRequest
import com.example.blankspace.data.retrofit.models.IgraSamResponse
import com.example.blankspace.data.retrofit.models.Izvodjac
import com.example.blankspace.data.retrofit.models.IzvodjaciZanra
import com.example.blankspace.data.retrofit.models.KorisniciResponse
import com.example.blankspace.data.retrofit.models.KrajDuelaRequest
import com.example.blankspace.data.retrofit.models.KrajDuelaResponse
import com.example.blankspace.data.retrofit.models.KrajIgreRequest
import com.example.blankspace.data.retrofit.models.KrajIgreResponse
import com.example.blankspace.data.retrofit.models.LoginRequest
import com.example.blankspace.data.retrofit.models.LoginResponse
import com.example.blankspace.data.retrofit.models.MojProfilRequest
import com.example.blankspace.data.retrofit.models.MojProfilResponse
import com.example.blankspace.data.retrofit.models.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.models.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaRequset
import com.example.blankspace.data.retrofit.models.PredlaganjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeRequset
import com.example.blankspace.data.retrofit.models.PredlaganjePesmeResponse
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.PredloziPesamaOdbijRequest
import com.example.blankspace.data.retrofit.models.PredloziPesamaResponse
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiResponse
import com.example.blankspace.data.retrofit.models.ProveriSifruRequest
import com.example.blankspace.data.retrofit.models.ProveriSifruResponse
import com.example.blankspace.data.retrofit.models.RangListaResponse
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import com.example.blankspace.data.retrofit.models.StigaoIgracRequest
import com.example.blankspace.data.retrofit.models.StigaoIgracResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import com.example.blankspace.data.retrofit.models.Zanr
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

const val BASE_URL = "http://192.168.143.178:8000/"
//const val BASE_URL = "http://10.0.2.2:8000/"
const val BASE_URL_LOCALHOST ="http://127.0.0.1:8000/"

interface Api {
    @GET("zanrovi")
    suspend fun getZanrovi(): List<Zanr>

    @GET("izvodjaci_andoid")
    suspend fun getIzvodjaci(): List<Izvodjac>

    @POST("igra_sam_android/")
    suspend fun getIgraSamData(@Body request: IgraSamRequest): IgraSamResponse

    @GET("rang_lista_andoid")
    suspend fun getRangLista(): List<RangListaResponse>

    @POST("pregled_profila_andoid/")
    suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse

    @POST("get_audio/")
    suspend fun getAudio(@Body url:AudioRequest):AudioResponse

    @POST("login_android/")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("predlaganje_izvodjaca_android/")
    suspend fun predlaganje_izvodjaca(@Body predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset):
            PredlaganjeIzvodjacaResponse

    @POST("predlaganje_pesme_android/")
    suspend fun predlaganje_pesme(@Body predlaganjePesmeRequset: PredlaganjePesmeRequset):
            PredlaganjePesmeResponse

    @POST("izvodjaci_zanra_andoid/")
    suspend fun getIzvodjaciZanra(@Body request: Zanr): List<IzvodjaciZanra>

    @POST("izvodjaci_pesme_andoid/")
    suspend fun getPesmeIzvodjaca(@Body request: Izvodjac): List<PesmeIzvodjaca>

    @POST("poeni_igre_android/")
    suspend fun postPoeniIgre(@Body request: Zanr): List<IzvodjaciZanra>

    @GET("uklanjanje_korisnika_android/")
    suspend fun getKorisniciUklanjanje():List<KorisniciResponse>

    @POST("registracija_android/")
    suspend fun postRegistracija(@Body request: RegistracijaRequest): RegistracijaResponse

    @POST("zaboravljena_lozinka_android/")
    suspend fun postZaboravljenaLozinka(@Body request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse

    @POST("zaboravljena_lozinka_pitanje_android/")
    suspend fun postZaboravljenaLozinkaPitanje(@Body request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse

    @POST("nova_lozinka_android/")
    suspend fun postNovaLozinka(@Body request: NovaLozinkaRequest): NovaLozinkaResponse

    @GET("predlozi_izvodjaca_android/")
    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>

    @POST("predlozi_izvodjaca_odbij_android/")
    suspend fun odbijPredlogIzvodjaca(@Body request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>

    @GET("predlozi_pesme_android/")
    suspend fun getPredloziPesme():List<PredloziPesamaResponse>

    @POST("predlozi_pesme_odbij_android/")
    suspend fun odbijPredlogPesme(@Body request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>

    // duel

    @POST("generisi_sifru_sobe_android/")
    suspend fun generisiSifru(@Body request: GenerisiSifruRequest): GenerisiSifruResponse

    @POST("proveri_sifru_sobe_android/")
    suspend fun proveriSifruSobe(@Body request: ProveriSifruRequest): ProveriSifruResponse

    @POST("stigao_igrac_android/")
    suspend fun stigaoIgrac(@Body request: StigaoIgracRequest): StigaoIgracResponse

    @POST("duel_android/")
    suspend fun duel(@Body request: DuelRequest): DuelResponse

    @POST("kraj_igre_adndroid/")
    suspend fun krajIgre(@Body request: KrajIgreRequest):KrajIgreResponse

    @POST("ukloni_korisnika_android/")
    suspend fun uklanjanjeKorisnika(@Body request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse

    @POST("ukloni_zanr_android/")
    suspend fun uklanjanjeZanra(@Body request: UklanjanjeZanraRequest): UklanjanjeZanraResponse

    @POST("ukloni_izvodjaca_android/")
    suspend fun uklanjanjeIzvodjaca(@Body request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse

    @POST("ukloni_pesmu_android/")
    suspend fun uklanjanjePesme(@Body request: UklanjanjePesmeRequest): UklanjanjePesmeResponse

    @POST("provera_da_li_postoji/")
    suspend fun provera_da_li_postoji(@Body request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse

    @POST("dodaj_zanr_android/")
    suspend fun dodajZanr(@Body request: DodajZanrRequest): DodajZanrResponse

    @POST("cekanje_rezultata_android/")
    suspend fun cekanjeRezultata(@Body request: CekanjeRezultataRequst):CekanjeRezultataResponse

    @POST("kraj_duela_android/")
    suspend fun krajDuela(@Body request: KrajDuelaRequest):KrajDuelaResponse
}

