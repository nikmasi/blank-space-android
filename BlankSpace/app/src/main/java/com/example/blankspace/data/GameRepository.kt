package com.example.blankspace.data

import com.example.blankspace.data.retrofit.models.AudioRequest
import com.example.blankspace.data.retrofit.models.AudioResponse
import com.example.blankspace.data.retrofit.models.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.models.CekanjeRezultataResponse
import com.example.blankspace.data.retrofit.models.DuelRequest
import com.example.blankspace.data.retrofit.models.DuelResponse
import com.example.blankspace.data.retrofit.models.GenerisiSifruRequest
import com.example.blankspace.data.retrofit.models.GenerisiSifruResponse
import com.example.blankspace.data.retrofit.models.IgraSamRequest
import com.example.blankspace.data.retrofit.models.IgraSamResponse
import com.example.blankspace.data.retrofit.models.KrajDuelaRequest
import com.example.blankspace.data.retrofit.models.KrajDuelaResponse
import com.example.blankspace.data.retrofit.models.KrajIgreRequest
import com.example.blankspace.data.retrofit.models.KrajIgreResponse
import com.example.blankspace.data.retrofit.models.ProveriSifruRequest
import com.example.blankspace.data.retrofit.models.ProveriSifruResponse
import com.example.blankspace.data.retrofit.models.StigaoIgracRequest
import com.example.blankspace.data.retrofit.models.StigaoIgracResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GameRepository {
    suspend fun getIgraSamData(request: IgraSamRequest): IgraSamResponse
    suspend fun krajIgre(request: KrajIgreRequest):KrajIgreResponse
    suspend fun getAudio(url:AudioRequest):AudioResponse


    suspend fun generisiSifru( request: GenerisiSifruRequest): GenerisiSifruResponse
    suspend fun proveriSifruSobe(request: ProveriSifruRequest): ProveriSifruResponse
    suspend fun stigaoIgrac(request: StigaoIgracRequest): StigaoIgracResponse
    suspend fun duel(request: DuelRequest): DuelResponse
    suspend fun cekanjeRezultata(request: CekanjeRezultataRequst):CekanjeRezultataResponse
    suspend fun krajDuela(request: KrajDuelaRequest):KrajDuelaResponse
}