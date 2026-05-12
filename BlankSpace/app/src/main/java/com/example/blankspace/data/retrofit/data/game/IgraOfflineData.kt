package com.example.blankspace.data.retrofit.data.game

data class IgraOfflineData(
    val stihpoznat: List<String>,
    val crtice: String,
    val tacno: String,
    val izvodjac: String,
    val zvuk: String,
    val pesma: String,
    var runda: Int,
    val poeni: Int,
    val listaBilo:List<Int>
)