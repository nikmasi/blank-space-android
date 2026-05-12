package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


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