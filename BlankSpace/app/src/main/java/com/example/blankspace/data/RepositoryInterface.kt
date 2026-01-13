package com.example.blankspace.data

import com.example.blankspace.data.retrofit.models.AudioRequest
import com.example.blankspace.data.retrofit.models.AudioResponse
import com.example.blankspace.data.retrofit.models.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.models.CekanjeRezultataResponse
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
import com.example.blankspace.data.retrofit.models.KorisnikPregledRequest
import com.example.blankspace.data.retrofit.models.KorisnikPregledResponse
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
import com.example.blankspace.data.retrofit.models.Pesma
import com.example.blankspace.data.retrofit.models.PesmeIzvodjaca
import com.example.blankspace.data.retrofit.models.PesmePoIzvodjacimaResponse
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
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiRequest
import com.example.blankspace.data.retrofit.models.ProveraDaLiPostojiResponse
import com.example.blankspace.data.retrofit.models.ProveriSifruRequest
import com.example.blankspace.data.retrofit.models.ProveriSifruResponse
import com.example.blankspace.data.retrofit.models.RangListaResponse
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import com.example.blankspace.data.retrofit.models.StatistikaResponse
import com.example.blankspace.data.retrofit.models.StigaoIgracRequest
import com.example.blankspace.data.retrofit.models.StigaoIgracResponse
import com.example.blankspace.data.retrofit.models.Stih
import com.example.blankspace.data.retrofit.models.StihoviPoPesmamaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeIzvodjacaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeKorisnikaResponse
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeRequest
import com.example.blankspace.data.retrofit.models.UklanjanjePesmeResponse
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraRequest
import com.example.blankspace.data.retrofit.models.UklanjanjeZanraResponse
import com.example.blankspace.data.retrofit.models.WebScrapperRequest
import com.example.blankspace.data.retrofit.models.WebScrapperResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import com.example.blankspace.data.retrofit.models.Zanr
import com.example.blankspace.data.retrofit.models.ZanrNazivRequest
import okhttp3.MultipartBody
import retrofit2.http.Body

interface RepositoryInterface {
    suspend fun getZanrovi():List<Zanr>

    suspend fun dohvati_izvodjace_zanra(request:ZanrNazivRequest):List<IzvodjaciZanra>

    suspend fun web_scrapper(request: WebScrapperRequest): List<WebScrapperResponse>

    suspend fun getIgraSamData(request: IgraSamRequest): IgraSamResponse

    suspend fun getRangLista(): List<RangListaResponse>

    suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse

    suspend fun getIzvodjaci(): List<Izvodjac>

    suspend fun getPesme(): List<Pesma>

    suspend fun getStihovi(): List<Stih>

    suspend fun getAudio(request:AudioRequest):AudioResponse

    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    suspend fun predlaganje_izvodjaca(@Body predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset):
            PredlaganjeIzvodjacaResponse

    suspend fun predlaganje_pesme(@Body predlaganjePesmeRequset: PredlaganjePesmeRequset):
            PredlaganjePesmeResponse

    suspend fun predlaganje_pretrazi(@Body predlaganjePretraziRequest: PredlaganjePretraziRequest):
            PredlaganjePretraziResponse


    suspend fun getIzvodjaciZanra(@Body request: Zanr): List<IzvodjaciZanra>

    suspend fun getPesmeIzvodjaca(@Body request: Izvodjac): List<PesmeIzvodjaca>

    suspend fun getKorisniciUklanjanje():List<KorisniciResponse>

    suspend fun postRegistracija(@Body request: RegistracijaRequest): RegistracijaResponse

    suspend fun postZaboravljenaLozinka(@Body request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse

    suspend fun postZaboravljenaLozinkaPitanje(@Body request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse

    suspend fun postNovaLozinka(@Body request: NovaLozinkaRequest): NovaLozinkaResponse

    suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse>

    suspend fun odbijPredlogIzvodjaca(@Body request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>

    suspend fun getPredloziPesme():List<PredloziPesamaResponse>

    suspend fun odbijPredlogPesme(@Body request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>

    suspend fun generisiSifru(@Body request: GenerisiSifruRequest): GenerisiSifruResponse

    suspend fun proveriSifruSobe(@Body request: ProveriSifruRequest): ProveriSifruResponse

    suspend fun stigaoIgrac(@Body request: StigaoIgracRequest): StigaoIgracResponse

    suspend fun duel(@Body request: DuelRequest): DuelResponse

    suspend fun krajIgre(@Body request: KrajIgreRequest): KrajIgreResponse

    suspend fun uklanjanjeKorisnika(@Body request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse

    suspend fun uklanjanjeZanra(@Body request: UklanjanjeZanraRequest): UklanjanjeZanraResponse
    suspend fun uklanjanjeIzvodjaca(@Body request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse

    suspend fun uklanjanjePesme(@Body request: UklanjanjePesmeRequest): UklanjanjePesmeResponse
    suspend fun provera_da_li_postoji(@Body request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse

    suspend fun dodajZanr( zanr: String,
                           izvodjac: String,
                           nazivPesme: String,
                           nepoznatiStihovi: String,
                           poznatiStihovi: String,
                           nivo: String,
                           zvuk: MultipartBody.Part): DodajZanrResponse

    suspend fun cekanjeRezultata(@Body request: CekanjeRezultataRequst): CekanjeRezultataResponse

    suspend fun krajDuela(@Body request: KrajDuelaRequest): KrajDuelaResponse

    suspend fun getPesmePoIzvodjacima():List<PesmePoIzvodjacimaResponse>

    suspend fun getStihoviPoPesmama():List<StihoviPoPesmamaResponse>

    suspend fun getStatistika():StatistikaResponse

    suspend fun getPregledKorisnik(@Body requst: KorisnikPregledRequest): KorisnikPregledResponse
}