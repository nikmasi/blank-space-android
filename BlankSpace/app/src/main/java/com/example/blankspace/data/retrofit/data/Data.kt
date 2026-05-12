package com.example.blankspace.data.retrofit.data

import okhttp3.MultipartBody

// TODO: This file should be split into multiple feature-based subfolders
//  (e.g. auth, uklanjanje, etc.) to improve maintainability and follow a clean architecture structure.

data class RangListaResponse(
    val index:Int,
    val korisnicko_ime:String,
    val rang_poeni:String
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

data class PesmePoIzvodjacimaResponse(
    val naziv:String,
    val ime_izvodjaca:String
)

data class StihoviPoPesmamaResponse(
    val poznat_tekst: String,
    val nepoznat_tekst: String,
    val zvuk_ime: String,
    val pesma:String
)