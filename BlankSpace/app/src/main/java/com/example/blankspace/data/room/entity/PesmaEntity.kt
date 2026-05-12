package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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