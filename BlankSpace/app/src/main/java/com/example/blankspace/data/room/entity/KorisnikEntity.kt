package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "korisnik")
data class KorisnikEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "korisnicko_ime") val korisnickoIme: String,
    val sifra: String,
    val ime: String,
    val prezime: String,
    val tip: String,
    @ColumnInfo(name = "rang_poeni") val rangPoeni: Int?,
    @ColumnInfo(name = "licni_poeni") val licniPoeni: Int?,
    @ColumnInfo(name = "poslednja_aktivnost") val poslednjaAktivnost: String?, // moze i Date uz TypeConverter
    @ColumnInfo(name = "odgovor_lozinka") val odgovorLozinka: String,
    @ColumnInfo(name = "pitanje_lozinka") val pitanjeLozinka: String
)