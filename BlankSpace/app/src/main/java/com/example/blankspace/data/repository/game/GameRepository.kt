package com.example.blankspace.data.repository.game

import com.example.blankspace.data.retrofit.data.audio.AudioRequest
import com.example.blankspace.data.retrofit.data.audio.AudioResponse
import com.example.blankspace.data.retrofit.data.duel.CekanjeRezultataRequst
import com.example.blankspace.data.retrofit.data.duel.CekanjeRezultataResponse
import com.example.blankspace.data.retrofit.data.duel.DuelRequest
import com.example.blankspace.data.retrofit.data.duel.DuelResponse
import com.example.blankspace.data.retrofit.data.duel.GenerisiSifruRequest
import com.example.blankspace.data.retrofit.data.duel.GenerisiSifruResponse
import com.example.blankspace.data.retrofit.data.game.IgraSamRequest
import com.example.blankspace.data.retrofit.data.game.IgraSamResponse
import com.example.blankspace.data.retrofit.data.duel.KrajDuelaRequest
import com.example.blankspace.data.retrofit.data.duel.KrajDuelaResponse
import com.example.blankspace.data.retrofit.data.game.KrajIgreRequest
import com.example.blankspace.data.retrofit.data.game.KrajIgreResponse
import com.example.blankspace.data.retrofit.data.duel.ProveriSifruRequest
import com.example.blankspace.data.retrofit.data.duel.ProveriSifruResponse
import com.example.blankspace.data.retrofit.data.duel.StigaoIgracRequest
import com.example.blankspace.data.retrofit.data.duel.StigaoIgracResponse

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