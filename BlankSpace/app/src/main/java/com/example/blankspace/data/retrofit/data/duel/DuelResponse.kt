package com.example.blankspace.data.retrofit.data.duel

data class DuelResponse(
    val stihpoznat: List<String>,
    val crtice: String,
    val tacno: String,
    val zvuk: String,
    val runda: Int,
    val poeni: Int,
    var rundePoeni:List<Int>
)