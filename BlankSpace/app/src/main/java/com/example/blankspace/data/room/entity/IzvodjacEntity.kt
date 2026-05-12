package com.example.blankspace.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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