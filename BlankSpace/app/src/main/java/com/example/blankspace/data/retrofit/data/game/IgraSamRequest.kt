package com.example.blankspace.data.retrofit.data.game

import com.google.gson.annotations.SerializedName

data class IgraSamRequest(
    @SerializedName("zanrovi") val zanrovi: List<Int>,
    @SerializedName("tezina") val tezina: String,
    @SerializedName("runda") val runda: Int,
    @SerializedName("poeni") val poeni: Int,
    @SerializedName("listaBilo") val listaBilo: List<Int>
)