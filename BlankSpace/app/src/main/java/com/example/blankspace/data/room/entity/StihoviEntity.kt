package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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