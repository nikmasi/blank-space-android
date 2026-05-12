package com.example.blankspace.data.retrofit.data.user

import com.example.blankspace.data.retrofit.data.user.Mecevi
import com.example.blankspace.data.retrofit.data.user.PesmeMeceva
import com.example.blankspace.data.retrofit.data.user.Protivnik


data class KorisnikPregledResponse(
    val ukupnoDuela:Int,
    val ukupnoPobeda:Int,
    val ukupnoPoraza:Int,
    val ukupnoNereseno:Int,
    val protivnici: List<Protivnik>,
    val mecevi: List<Mecevi>,
    val pesmeMeceva: List<PesmeMeceva>
)