package com.example.blankspace.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zanr")
data class ZanrEntity(
    @PrimaryKey val id: Int,
    val naziv: String
)