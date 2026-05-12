package com.example.blankspace.data.retrofit.data.duel

data class KrajDuelaResponse(
    val poeni:List<Int>,
    val poeni_runde:List<List<Int>>,
    val igrac1:String,
    val igrac2:String,
    val ulogovan:Boolean
)