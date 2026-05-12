package com.example.blankspace.data.retrofit.data.duel

data class DuelRequest(
    val runda: Int,
    val poeni: Int,
    val stihovi: String,
    val rundePoeni:List<Int>
)