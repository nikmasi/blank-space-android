package com.example.blankspace.data.retrofit.api

import com.example.blankspace.data.retrofit.data.audio.AudioRequest
import com.example.blankspace.data.retrofit.data.audio.AudioResponse
import com.example.blankspace.data.retrofit.data.duel.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.data.duel.CekanjeRezultataResponse
import com.example.blankspace.data.retrofit.data.DodajZanrResponse
import com.example.blankspace.data.retrofit.data.duel.DuelRequest
import com.example.blankspace.data.retrofit.data.duel.DuelResponse
import com.example.blankspace.data.retrofit.data.duel.GenerisiSifruRequest
import com.example.blankspace.data.retrofit.data.duel.GenerisiSifruResponse
import com.example.blankspace.data.retrofit.data.game.IgraSamRequest
import com.example.blankspace.data.retrofit.data.game.IgraSamResponse
import com.example.blankspace.data.retrofit.data.music.Izvodjac
import com.example.blankspace.data.retrofit.data.IzvodjaciZanra
import com.example.blankspace.data.retrofit.data.user.KorisniciResponse
import com.example.blankspace.data.retrofit.data.user.KorisnikPregledRequest
import com.example.blankspace.data.retrofit.data.user.KorisnikPregledResponse
import com.example.blankspace.data.retrofit.data.duel.KrajDuelaRequest
import com.example.blankspace.data.retrofit.data.duel.KrajDuelaResponse
import com.example.blankspace.data.retrofit.data.game.KrajIgreRequest
import com.example.blankspace.data.retrofit.data.game.KrajIgreResponse
import com.example.blankspace.data.retrofit.data.LoginRequest
import com.example.blankspace.data.retrofit.data.LoginResponse
import com.example.blankspace.data.retrofit.data.user.MojProfilRequest
import com.example.blankspace.data.retrofit.data.user.MojProfilResponse
import com.example.blankspace.data.retrofit.data.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.data.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.data.music.Pesma
import com.example.blankspace.data.retrofit.data.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.data.PesmePoIzvodjacimaResponse
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
import com.example.blankspace.data.retrofit.data.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.data.ProveraDaLiPostojiResponse
import com.example.blankspace.data.retrofit.data.duel.ProveriSifruRequest
import com.example.blankspace.data.retrofit.data.duel.ProveriSifruResponse
import com.example.blankspace.data.retrofit.data.RangListaResponse
import com.example.blankspace.data.retrofit.data.RegistracijaRequest
import com.example.blankspace.data.retrofit.data.RegistracijaResponse
import com.example.blankspace.data.retrofit.data.stats.StatistikaResponse
import com.example.blankspace.data.retrofit.data.duel.StigaoIgracRequest
import com.example.blankspace.data.retrofit.data.duel.StigaoIgracResponse
import com.example.blankspace.data.retrofit.data.music.Stih
import com.example.blankspace.data.retrofit.data.StihoviPoPesmamaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.data.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.data.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.data.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.data.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.data.scraper.WebScrapperRequest
import com.example.blankspace.data.retrofit.data.scraper.WebScrapperResponse
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaResponse
import com.example.blankspace.data.retrofit.data.music.Zanr
import com.example.blankspace.data.retrofit.data.music.ZanrNazivRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

const val BASE_URL = "http://192.168.0.12:8000/"
//const val BASE_URL = "http://10.0.2.2:8000/"
const val BASE_URL_LOCALHOST ="http://127.0.0.1:8000/"

// TODO: Remove this file. Logic has been split into
//  AdminApi, AuthApi, ContentApi, GameApi, and SuggestionApi.

interface Api {
    @GET("zanrovi")
    suspend fun getZanrovi(): List<Zanr>

    @POST("dohvati_izvodjace_zanra_android/")
    suspend fun dohvati_izvodjace_zanra(@Body request: ZanrNazivRequest):List<IzvodjaciZanra>

    @POST("web_scrapper_android/")
    suspend fun web_scrapper(@Body request: WebScrapperRequest): List<WebScrapperResponse>

    @GET("izvodjaci_andoid")
    suspend fun getIzvodjaci(): List<Izvodjac>

    @GET("pesme_android")
    suspend fun getPesme(): List<Pesma>

    @GET("stihovi_android")
    suspend fun getStihovi(): List<Stih>

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

    @POST("predlaganje_pretrazi_android/")
    suspend fun predlaganje_pretrazi(@Body predlaganjePretraziRequest: PredlaganjePretraziRequest):
            PredlaganjePretraziResponse

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

    @Multipart
    @POST("dodaj_zanr_android/")
    suspend fun dodajZanr(@Part("zanr") zanr: RequestBody,
                          @Part("izvodjac") izvodjac: RequestBody,
                          @Part("naziv_pesme") nazivPesme: RequestBody,
                          @Part("nepoznati_stihovi") nepoznatiStihovi: RequestBody,
                          @Part("poznati_stihovi") poznatiStihovi: RequestBody,
                          @Part("nivo") nivo: RequestBody,
                          @Part zvuk: MultipartBody.Part): DodajZanrResponse

    @POST("cekanje_rezultata_android/")
    suspend fun cekanjeRezultata(@Body request: CekanjeRezultataRequst):CekanjeRezultataResponse

    @POST("kraj_duela_android/")
    suspend fun krajDuela(@Body request: KrajDuelaRequest):KrajDuelaResponse

    @GET("pesme_po_izvodjacima_android/")
    suspend fun getPesmePoIzvodjacima():List<PesmePoIzvodjacimaResponse>

    @GET("stihovi_po_pesmama_android/")
    suspend fun getStihoviPoPesmama():List<StihoviPoPesmamaResponse>

    @GET("statistika_android/")
    suspend fun getStatistika():StatistikaResponse

    @POST("pregledKorisnik_android/")
    suspend fun getPregledKorisnik(@Body requst: KorisnikPregledRequest):KorisnikPregledResponse
}