package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


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