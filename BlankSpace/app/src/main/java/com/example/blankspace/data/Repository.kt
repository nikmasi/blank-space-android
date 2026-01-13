package com.example.blankspace.data

import com.example.blankspace.data.retrofit.Api
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import javax.inject.Inject

class Repository @Inject constructor(
    private val Api: Api,
): RepositoryInterface {
    override suspend fun getZanrovi():List<Zanr> = Api.getZanrovi()

    override suspend fun dohvati_izvodjace_zanra(request:ZanrNazivRequest):List<IzvodjaciZanra> = Api.dohvati_izvodjace_zanra(request)

    override suspend fun web_scrapper(request: WebScrapperRequest): List<WebScrapperResponse> = Api.web_scrapper(request)

    override suspend fun getIgraSamData(request: IgraSamRequest): IgraSamResponse = Api.getIgraSamData(request)

    override suspend fun getRangLista(): List<RangListaResponse> = Api.getRangLista()

    override suspend fun getMojProfilData(@Body request: MojProfilRequest): MojProfilResponse=Api.getMojProfilData(request)

    override suspend fun getIzvodjaci(): List<Izvodjac> = Api.getIzvodjaci()

    override suspend fun getPesme(): List<Pesma> = Api.getPesme()

    override suspend fun getStihovi(): List<Stih> = Api.getStihovi()

    override suspend fun getAudio(request:AudioRequest):AudioResponse = Api.getAudio(request)

    override suspend fun login(@Body credentials: LoginRequest): LoginResponse = Api.login(credentials)

    override suspend fun predlaganje_izvodjaca(@Body predlaganjeIzvodjacaRequset: PredlaganjeIzvodjacaRequset):
            PredlaganjeIzvodjacaResponse = Api.predlaganje_izvodjaca(predlaganjeIzvodjacaRequset)

    override suspend fun predlaganje_pesme(@Body predlaganjePesmeRequset: PredlaganjePesmeRequset):
            PredlaganjePesmeResponse = Api.predlaganje_pesme(predlaganjePesmeRequset)

    override suspend fun predlaganje_pretrazi(@Body predlaganjePretraziRequest: PredlaganjePretraziRequest):
            PredlaganjePretraziResponse = Api.predlaganje_pretrazi(predlaganjePretraziRequest)


    override suspend fun getIzvodjaciZanra(@Body request: Zanr): List<IzvodjaciZanra> = Api.getIzvodjaciZanra(request)

    override suspend fun getPesmeIzvodjaca(@Body request: Izvodjac): List<PesmeIzvodjaca> = Api.getPesmeIzvodjaca(request)

    override suspend fun getKorisniciUklanjanje():List<KorisniciResponse> = Api.getKorisniciUklanjanje()

    override suspend fun postRegistracija(@Body request: RegistracijaRequest): RegistracijaResponse = Api.postRegistracija(request)

    override suspend fun postZaboravljenaLozinka(@Body request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse
            = Api.postZaboravljenaLozinka(request)

    override suspend fun postZaboravljenaLozinkaPitanje(@Body request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse
            = Api.postZaboravljenaLozinkaPitanje(request)

    override suspend fun postNovaLozinka(@Body request: NovaLozinkaRequest): NovaLozinkaResponse = Api.postNovaLozinka(request)

    override suspend fun getPredloziIzvodjaca():List<PredloziIzvodjacaResponse> = Api.getPredloziIzvodjaca()

    override suspend fun odbijPredlogIzvodjaca(@Body request: PredloziIzvodjacaOdbijRequest): List<PredloziIzvodjacaResponse>
        = Api.odbijPredlogIzvodjaca(request)

    override suspend fun getPredloziPesme():List<PredloziPesamaResponse> = Api.getPredloziPesme()

    override suspend fun odbijPredlogPesme(@Body request: PredloziPesamaOdbijRequest): List<PredloziPesamaResponse>
       = Api.odbijPredlogPesme(request)

    override suspend fun generisiSifru(@Body request: GenerisiSifruRequest): GenerisiSifruResponse = Api.generisiSifru(request)

    override suspend fun proveriSifruSobe(@Body request: ProveriSifruRequest): ProveriSifruResponse = Api.proveriSifruSobe(request)

    override suspend fun stigaoIgrac(@Body request: StigaoIgracRequest): StigaoIgracResponse = Api.stigaoIgrac(request)

    override suspend fun duel(@Body request: DuelRequest): DuelResponse =Api.duel(request)

    override suspend fun krajIgre(@Body request: KrajIgreRequest): KrajIgreResponse =Api.krajIgre(request)

    override suspend fun uklanjanjeKorisnika(@Body request: UklanjanjeKorisnikaRequest): UklanjanjeKorisnikaResponse =
        Api.uklanjanjeKorisnika(request)

    override suspend fun uklanjanjeZanra(@Body request: UklanjanjeZanraRequest): UklanjanjeZanraResponse= Api.uklanjanjeZanra(request)

    override suspend fun uklanjanjeIzvodjaca(@Body request: UklanjanjeIzvodjacaRequest): UklanjanjeIzvodjacaResponse
        = Api.uklanjanjeIzvodjaca(request)

    override suspend fun uklanjanjePesme(@Body request: UklanjanjePesmeRequest): UklanjanjePesmeResponse = Api.uklanjanjePesme(request)


    override suspend fun provera_da_li_postoji(@Body request: ProveraDaLiPostojiRequest): ProveraDaLiPostojiResponse
        =Api.provera_da_li_postoji(request)

    override suspend fun dodajZanr( zanr: String,
                           izvodjac: String,
                           nazivPesme: String,
                           nepoznatiStihovi: String,
                           poznatiStihovi: String,
                           nivo: String,
                           zvuk: MultipartBody.Part): DodajZanrResponse = Api.dodajZanr(
        zanr = zanr.toRequestBody("text/plain".toMediaType()),
        izvodjac = izvodjac.toRequestBody("text/plain".toMediaType()),
        nazivPesme = nazivPesme.toRequestBody("text/plain".toMediaType()),
        nepoznatiStihovi = nepoznatiStihovi.toRequestBody("text/plain".toMediaType()),
        poznatiStihovi = poznatiStihovi.toRequestBody("text/plain".toMediaType()),
        nivo = nivo.toRequestBody("text/plain".toMediaType()),
        zvuk = zvuk
    )

    override suspend fun cekanjeRezultata(@Body request: CekanjeRezultataRequst): CekanjeRezultataResponse
        = Api.cekanjeRezultata(request)

    override suspend fun krajDuela(@Body request: KrajDuelaRequest): KrajDuelaResponse = Api.krajDuela(request)

    override suspend fun getPesmePoIzvodjacima():List<PesmePoIzvodjacimaResponse> = Api.getPesmePoIzvodjacima()

    override suspend fun getStihoviPoPesmama():List<StihoviPoPesmamaResponse> = Api.getStihoviPoPesmama()

    override suspend fun getStatistika():StatistikaResponse = Api.getStatistika()

    override suspend fun getPregledKorisnik(@Body requst: KorisnikPregledRequest): KorisnikPregledResponse = Api.getPregledKorisnik(requst)
}