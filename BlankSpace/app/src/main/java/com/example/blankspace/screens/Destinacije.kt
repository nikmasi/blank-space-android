package com.example.blankspace.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinacije(val ime:String, val ruta:String,val ikonica:ImageVector){
    object UcitavanjeEkrana:Destinacije(
        ime="UcitavanjeEkrana",
        ruta="UcitavanjeEkrana",
        ikonica = Icons.Default.FavoriteBorder
    )
    object Pocetna: Destinacije(
        ime="pocetna",
        ruta="pocetna",
        ikonica = Icons.Default.Home
    )
    object PocetnaBrucos: Destinacije(
        ime="pocetna_brucos",
        ruta="pocetna_brucos",
        ikonica = Icons.Default.Home
    )
    object PocetnaStudent: Destinacije(
        ime="pocetna_student",
        ruta="pocetna_student",
        ikonica = Icons.Default.Home
    )
    object PocetnaMaster: Destinacije(
        ime="pocetna_master",
        ruta="pocetna_master",
        ikonica = Icons.Default.Home
    )
    object PocetnaOffline: Destinacije(
        ime="pocetna_offline",
        ruta="pocetna_offline",
        ikonica = Icons.Default.Home
    )
    object PocetnaAdmin: Destinacije(
        ime="pocetna_admin",
        ruta="pocetna_admin",
        ikonica = Icons.Default.Home
    )
    object PravilaIgre: Destinacije(
        ime="pravila_igre",
        ruta="pravila_igre",
        ikonica = Icons.Default.Home
    )
    object Nivo_igra_sam: Destinacije(
        ime="nivo_igra_sam",
        ruta="nivo_igra_sam",
        ikonica = Icons.Default.Home
    )
    object Nivo_igra_offline: Destinacije(
        ime="nivo_igra_offline",
        ruta="nivo_igra_offline",
        ikonica = Icons.Default.Home
    )
    object Zanr_igra_offline: Destinacije(
        ime="zanr_igra_offline",
        ruta="zanr_igra_offline",
        ikonica = Icons.Default.Home
    )
    object Igra_offline: Destinacije(
        ime="igra_offline",
        ruta="igra_offline",
        ikonica = Icons.Default.Home
    )
    object Sifra_sobe_duel: Destinacije(
        ime="sifra_sobe_duel",
        ruta="sifra_sobe_duel",
        ikonica = Icons.Default.Home
    )
    object Zanr_igra_sam: Destinacije(
        ime="zanr_igra_sam",
        ruta="zanr_igra_sam",
        ikonica = Icons.Default.Home
    )
    object Igra_sam : Destinacije(
        ime="igra_sam",
        ruta="igra_sam/{selectedZanrovi}",
        ikonica = Icons.Default.Home
    )

    object Kraj_igre_igre_sam: Destinacije(
        ime="kraj_igre_igre_sam",
        ruta="kraj_igre_igre_sam",
        ikonica = Icons.Default.Home
    )
    object Kraj_igre_offline: Destinacije(
        ime="kraj_igre_offline",
        ruta="kraj_igre_offline",
        ikonica = Icons.Default.Home
    )
    object Login: Destinacije(
        ime="login",
        ruta="login",
        ikonica = Icons.Default.Home
    )
    object Registracija: Destinacije(
        ime="registracija",
        ruta="registracija",
        ikonica = Icons.Default.Home
    )
    object ZaboravljenaLozinka: Destinacije(
        ime="zaboravljenaLozinka",
        ruta="zaboravljenaLozinka",
        ikonica = Icons.Default.Home
    )
    object ZaboravljenaLozinkaPitanje: Destinacije(
        ime="zaboravljenaLozinkaPitanje",
        ruta="zaboravljenaLozinkaPitanje",
        ikonica = Icons.Default.Home
    )
    object PromenaLozinke: Destinacije(
        ime="promenaLozinke",
        ruta="promenaLozinke",
        ikonica = Icons.Default.Home
    )
    object MojProfil: Destinacije(
        ime="moj_profil",
        ruta="moj_profil",
        ikonica = Icons.Default.AccountCircle
    )
    object RangLista: Destinacije(
        ime="rang_lista",
        ruta="rang_lista",
        ikonica = Icons.Default.Star
    )

    object PredlaganjeIzvodjaca: Destinacije(
        ime="predlaganje_izvodjaca",
        ruta="predlaganje_izvodjaca",
        ikonica = Icons.Default.Star
    )

    object PredlaganjePesme: Destinacije(
        ime="predlaganje_pesme",
        ruta="predlaganje_pesme",
        ikonica = Icons.Default.Star
    )

    object PretragaPredlaganje: Destinacije(
        ime="pretraga_predlaganje",
        ruta="pretraga_predlaganje",
        ikonica = Icons.Default.Star
    )

    object UklanjanjeZanra: Destinacije(
        ime="uklanjanje_zanra",
        ruta="uklanjanje_zanra",
        ikonica = Icons.Default.Star
    )
    object IzborZanraUklanjanjeIzvodjaca:Destinacije(
        ime="izborZanra_uklanjanjeIzvodjaca",
        ruta="izborZanra_uklanjanjeIzvodjaca",
        ikonica = Icons.Default.Star
    )
    object UklanjanjeIzvodjaca:Destinacije(
        ime="uklanjanjeIzvodjaca",
        ruta="uklanjanjeIzvodjaca",
        ikonica = Icons.Default.Star
    )
    object UklanjanjeKorisnika:Destinacije(
        ime="uklanjanje_korisnika",
        ruta="uklanjanje_korisnika",
        ikonica = Icons.Default.Star
    )
    object IzborZanraUklanjanjePesme:Destinacije(
        ime="izbor_zanra_uklanjanje_pesme",
        ruta="izbor_zanra_uklanjanje_pesme",
        ikonica = Icons.Default.Star
    )
    object IzborIzvodjacaUklanjanjePesme:Destinacije(
        ime="izbor_izvodjaca_uklanjanje_pesme",
        ruta="izbor_izvodjaca_uklanjanje_pesme",
        ikonica = Icons.Default.Star
    )
    object UklanjanjePesme:Destinacije(
        ime="uklanjanje_pesme",
        ruta="uklanjanje_pesme",
        ikonica = Icons.Default.Star
    )
    object PredloziIzvodjaca:Destinacije(
        ime="predlozi_izvodjaca",
        ruta="predlozi_izvodjaca",
        ikonica = Icons.Default.Star
    )
    object PredloziPesme:Destinacije(
        ime="predlozi_pesme",
        ruta="predlozi_pesme",
        ikonica = Icons.Default.Star
    )
    object PesmaPodaci:Destinacije(
        ime="pesma_podaci",
        ruta="pesma_podaci",
        ikonica = Icons.Default.Star
    )
    object PesmaPodaci2:Destinacije(
        ime="pesma_podaci2",
        ruta="pesma_podaci2",
        ikonica = Icons.Default.Star
    )
    object NazivZanra:Destinacije(
        ime="naziv_zanra",
        ruta="naziv_zanra",
        ikonica = Icons.Default.Star
    )
    object ImeIzvodjaca:Destinacije(
        ime="ime_izvodjaca",
        ruta="ime_izvodjaca/{zanr}",
        ikonica = Icons.Default.Star
    )
    object PesmaPodaciD:Destinacije(
        ime="pesma_podaci_d",
        ruta="pesma_podaci_d",
        ikonica = Icons.Default.Star
    )
    object IzborZanra:Destinacije(
        ime="izbor_zanra",
        ruta="izbor_zanra",
        ikonica = Icons.Default.Star
    )
    object IzborZanra2:Destinacije(
        ime="izbor_zanra2",
        ruta="izbor_zanra2",
        ikonica = Icons.Default.Star
    )
    object IzborIzvodjaca:Destinacije(
        ime="izbor_izvodjaca",
        ruta="izbor_izvodjaca/{selectedZanr}",
        ikonica = Icons.Default.Star
    )
    object Generisi_sifru_sobe:Destinacije(
        ime="generisi_sifru_sobe",
        ruta="generisi_sifru_sobe",
        ikonica = Icons.Default.Star
    )
    object Duel:Destinacije(
        ime="duel",
        ruta="duel",
        ikonica = Icons.Default.FavoriteBorder
    )
    object Kraj_duela:Destinacije(
        ime="kraj_duela",
        ruta="kraj_duela",
        ikonica = Icons.Default.FavoriteBorder
    )
    object Cekanje_rezultata:Destinacije(
        ime="cekanje_rezultata",
        ruta="cekanje_rezultata",
        ikonica = Icons.Default.FavoriteBorder
    )
}

val listaBrucos = listOf(
    Destinacije.PocetnaBrucos,
    Destinacije.RangLista,
    Destinacije.MojProfil
)

val listaStudent = listOf(
    Destinacije.PocetnaStudent,
    Destinacije.RangLista,
    Destinacije.MojProfil
)

val listaMaster = listOf(
    Destinacije.PocetnaMaster,
    Destinacije.RangLista,
    Destinacije.MojProfil
)