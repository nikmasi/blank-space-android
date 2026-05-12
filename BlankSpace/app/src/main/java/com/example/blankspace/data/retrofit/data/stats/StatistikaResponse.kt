package com.example.blankspace.data.retrofit.data.stats

data class StatistikaResponse(
    val ukupnoKorisnika:Int,
    val ukupnoPesama:Int,
    val ukupnoPredlogaPesamaNaCekanju:Int,
    val ukupnoPredlogaIzvodjacaNaCekanju:Int,
    val korisnikSaNajviseLicnihPoenaIme:String,
    val korisnikSaNajviseLicnihPoenaPoeni:Int,
    val korisnikSaNajviseRangPoenimaIme:String,
    val korisnikSaNajviseRangPoenimaPoeni:Int,
    val brojDuela:Int
)