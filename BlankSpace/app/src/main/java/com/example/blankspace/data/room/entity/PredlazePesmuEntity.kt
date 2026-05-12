package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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