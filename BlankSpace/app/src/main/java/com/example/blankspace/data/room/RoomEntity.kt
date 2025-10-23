package com.example.blankspace.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "zanr")
data class ZanrEntity(
    @PrimaryKey val id: Int,
    val naziv: String
)

@Entity(tableName = "izvodjac",
    foreignKeys = [ForeignKey(
        entity = ZanrEntity::class,
        parentColumns = ["id"],
        childColumns = ["zan_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("zan_id")]
)
data class IzvodjacEntity(
    @PrimaryKey val id: Int,
    val ime: String,
    @ColumnInfo(name = "zan_id") val zanId: Int
)

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

@Entity(
    tableName = "pesma",
    foreignKeys = [ForeignKey(
        entity = IzvodjacEntity::class,
        parentColumns = ["id"],
        childColumns = ["izv_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("izv_id")]
)
data class PesmaEntity(
    @PrimaryKey val id: Int,
    val naziv: String,
    @ColumnInfo(name = "izv_id") val izvId: Int
)

@Entity(
    tableName = "predlaze_izvodjaca",
    foreignKeys = [
        ForeignKey(
            entity = KorisnikEntity::class,
            parentColumns = ["id"],
            childColumns = ["kor_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ZanrEntity::class,
            parentColumns = ["id"],
            childColumns = ["zan_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("kor_id"), Index("zan_id")]
)
data class PredlazeIzvodjacaEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "ime_izvodjaca") val imeIzvodjaca: String,
    @ColumnInfo(name = "kor_id") val korId: Int,
    @ColumnInfo(name = "zan_id") val zanId: Int
)

@Entity(
    tableName = "predlaze_pesmu",
    foreignKeys = [
        ForeignKey(
            entity = IzvodjacEntity::class,
            parentColumns = ["id"],
            childColumns = ["izv_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = KorisnikEntity::class,
            parentColumns = ["id"],
            childColumns = ["kor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("izv_id"), Index("kor_id")]
)
data class PredlazePesmuEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "naziv_pesme") val nazivPesme: String,
    @ColumnInfo(name = "izv_id") val izvId: Int,
    @ColumnInfo(name = "kor_id") val korId: Int
)

@Entity(
    tableName = "soba",
    foreignKeys = [
        ForeignKey(
            entity = KorisnikEntity::class,
            parentColumns = ["id"],
            childColumns = ["kor_1_id"]
        ),
        ForeignKey(
            entity = KorisnikEntity::class,
            parentColumns = ["id"],
            childColumns = ["kor_2_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("kor_1_id"), Index("kor_2_id")]
)
data class SobaEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "kor_1_id") val kor1Id: Int?,
    @ColumnInfo(name = "kor_2_id") val kor2Id: Int?,
    @ColumnInfo(name = "poeni_1") val poeni1: Int,
    @ColumnInfo(name = "poeni_2") val poeni2: Int?,
    @ColumnInfo(name = "poeni_runde_1") val poeniRunde1: String?,
    @ColumnInfo(name = "poeni_runde_2") val poeniRunde2: String?,
    val stihovi: String
)

@Entity(
    tableName = "stihovi",
    foreignKeys = [ForeignKey(
        entity = PesmaEntity::class,
        parentColumns = ["id"],
        childColumns = ["pes_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("pes_id")]
)
data class StihoviEntity(
    @PrimaryKey val id: Int,
    val nivo: String,
    @ColumnInfo(name = "poznat_tekst") val poznatTekst: String,
    @ColumnInfo(name = "nepoznat_tekst") val nepoznatTekst: String,
    val zvuk: String,
    @ColumnInfo(name = "pes_id") val pesId: Int
)
