package com.example.blankspace.data.retrofit.models

import com.example.blankspace.viewModels.RedniBroj
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class Korisnik(
    val id: Int,
    val ime: String,
    val tip: String
)

data class Zanr(
    val id: Int,
    val naziv: String
)

data class ZanrNazivRequest(
    val zanr:String
)

data class WebScrapperRequest(
    val reci:String
)

data class WebScrapperResponse(
    val naslov: String,
    val izvodjac: String,
    val tekst: String
)

data class Izvodjac(
    val id:Int,
    val ime:String,
    val zan:Int
)

data class Pesma(
    val id:Int,
    val naziv:String,
    val izv:Int
)

data class Stih(
    val id:Int,
    val nivo:String,
    val poznat_tekst:String,
    val nepoznat_tekst:String,
    val zvuk_url:String,
    val pes:Int
)
data class ZanrResponse(
    val zanrovi: List<Zanr>
)

data class IgraSamRequest(
    @SerializedName("zanrovi") val zanrovi: List<Int>,
    @SerializedName("tezina") val tezina: String, // Koristim listu za nivo
    @SerializedName("runda") val runda: Int,
    @SerializedName("poeni") val poeni: Int,
    @SerializedName("listaBilo") val listaBilo: List<Int>
)

data class IgraSamResponse(
    val stihpoznat: List<String>,
    val crtice: String,
    val tacno: String,
    val izvodjac: String,
    val zvuk: String,
    val pesma: String,
    val runda: Int,
    val poeni: Int,
    val listaBilo:List<Int>
)

data class IgraOfflineData(
    val stihpoznat: List<String>,
    val crtice: String,
    val tacno: String,
    val izvodjac: String,
    val zvuk: String,
    val pesma: String,
    var runda: Int,
    val poeni: Int,
    val listaBilo:List<Int>
)

data class KrajIgreRequest(
    val korisnickoIme: String,
    val poeni_igra_sam:Int
)

data class KrajIgreResponse(
    val poeni: Int,
    val tip: String,
    val ulogovan:String
)

data class RangListaResponse(
    val index:Int,
    val korisnicko_ime:String,
    val rang_poeni:String
)

data class MojProfilRequest(
    val korisnicko_ime: String
)

data class MojProfilResponse(
    val ime:String,
    val prezime:String,
    val korisnicko_ime: String,
    val licni_poeni:Int,
    val rang_poeni:Int,
    val rank:String
)

data class AudioResponse(
    val audio_url: String
)

data class AudioRequest(
    val url:String
)


data class LoginRequest(
    val username: String,
    val password: String
)


data class LoginResponse(
    val access: String,
    val refresh: String?,
    val ime:String,
    val korisnicko_ime:String,
    val tip:String,
    val odgovor: String
)

data class PredlaganjeIzvodjacaRequset(
    val izvodjac: String,
    val zanr:String,
    val korisnicko_ime: String
)

data class PredlaganjeIzvodjacaResponse(
    val odgovor:String?
)

data class PredlaganjePretraziRequest(
    val naziv: String,
    val korisnicko_ime: String
)

data class PredlaganjePretraziResponse(
    val odgovor:String?
)

data class PredlaganjePesmeRequset(
    val pesma: String,
    val izvodjac: String,
    val zanr:String,
    val korisnicko_ime: String
)

data class PredlaganjePesmeResponse(
    val odgovor:String?
)

data class IzvodjaciZanra(
    val id:Int,
    val ime:String
)

data class PesmeIzvodjaca(
    val id:Int,
    val naziv:String
)

data class KorisniciResponse(
    val korisnicko_ime: String,
    val poslednja_aktivnost: String?
)

data class RegistracijaRequest(
    val ime_i_prezime:String,
    val korisnicko_ime: String,
    val lozinka:String,
    val potvrda_lozinke:String,
    val pitanje:String,
    val odgovor: String
)

data class RegistracijaResponse(
    val access: String,
    val refresh: String?,
    val ime:String,
    val korisnicko_ime:String,
    val tip:String,
    val odgovor: String
)

data class ZaboravljenaLozinkaRequest(
    val korisnicko_ime: String
)

data class ZaboravljenaLozinkaResponse(
    val korisnicko_ime:String,
    val pitanje_lozinka: String,
    val odgovor_lozinka: String,
    val tip: String,
    val odgovor: String
)

data class ZaboravljenaLozinkaPitanjeRequest(
    val korisnicko_ime: String,
    val odgovor: String
)

data class ZaboravljenaLozinkaPitanjeResponse(
    val odgovor: String
)

data class NovaLozinkaRequest(
    val korisnicko_ime: String,
    val lozinka: String,
    val potvrda_lozinke: String
)

data class NovaLozinkaResponse(
    val odgovor: String
)

data class PredloziIzvodjacaResponse(
    val id:Int,
    val ime_izvodjaca:String,
    val kor_ime:String,
    val zan_naziv: String,
    val odgovor: String
)

data class PredloziIzvodjacaOdbijRequest(
    val id:Int
)

data class PredloziPesamaResponse(
    val id:Int,
    val naziv_pesme:String,
    val izv_ime:String,
    val kor_ime: String,
    val zan_naziv: String,
    val odgovor: String
)

data class PredloziPesamaOdbijRequest(
    val id:Int
)

// duel

data class GenerisiSifruRequest(
    val korisnickoIme: String
)

data class GenerisiSifruResponse(
    val sifra:Int,
    val stihovi:String
)

data class ProveriSifruRequest(
    val korisnickoIme: String,
    val sifra: Int
)

data class ProveriSifruResponse(
    val stihovi: String,
    val zvuk: String,
    val crtice:String,
    val runda:Int,
    val poeni: Int,
    val error:String
)

data class StigaoIgracRequest(
    val sifra: Int
)

data class StigaoIgracResponse(
    val stigao:String
)

data class DuelRequest(
    val runda: Int,
    val poeni: Int,
    val stihovi: String,
    val rundePoeni:List<Int>
)

data class DuelResponse(
    val stihpoznat: List<String>,
    val crtice: String,
    val tacno: String,
    val zvuk: String,
    val runda: Int,
    val poeni: Int,
    var rundePoeni:List<Int>
)

data class CekanjeRezultataRequst(
    val rundaPoeni: List<Int>,
    val poeni: Int,
    val soba:Int,
    val redniBroj: Int
)

data class CekanjeRezultataResponse(
    val odgovor: String
)

data class KrajDuelaRequest(
    val rundaPoeni: List<Int>,
    val poeni: Int,
    val soba:Int,
    val redniBroj: Int,
    val upisuj:String
)
data class KrajDuelaResponse(
    val poeni:List<Int>,
    val poeni_runde:List<List<Int>>,
    val igrac1:String,
    val igrac2:String,
    val ulogovan:Boolean
)

data class UklanjanjeKorisnikaRequest(
    val korisnicko_ime: String
)

data class UklanjanjeKorisnikaResponse(
    val odgovor: String
)

data class UklanjanjeZanraRequest(
    val zanr: Int
)

data class UklanjanjeZanraResponse(
    val odgovor: String
)

data class UklanjanjeIzvodjacaRequest(
    val izvodjac: Int
)

data class UklanjanjeIzvodjacaResponse(
    val odgovor: String
)

data class UklanjanjePesmeRequest(
    val pesma: Int
)

data class UklanjanjePesmeResponse(
    val odgovor: String
)

data class ProveraDaLiPostojiRequest(
    val vrednost:String,
    val tip:String
)

data class ProveraDaLiPostojiResponse(
    var odgovor: String
)

data class DodajZanrRequest(
    val zanr: String,
    val izvodjac: String,
    val naziv_pesme: String,
    val nepoznati_stihovi:String,
    val poznati_stihovi:String,
    val nivo:String,
    val zvuk: MultipartBody.Part
)
data class DodajZanrResponse(
    val odgovor: String
)