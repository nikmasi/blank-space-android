package com.example.blankspace.data.repository.game

import com.example.blankspace.data.retrofit.api.GameApi
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
import jakarta.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameApi: GameApi
) : GameRepository {

    override suspend fun getIgraSamData(request: IgraSamRequest): IgraSamResponse = gameApi.getIgraSamData(request)
    override suspend fun krajIgre(request: KrajIgreRequest): KrajIgreResponse = gameApi.krajIgre(request)
    override suspend fun getAudio(url: AudioRequest): AudioResponse = gameApi.getAudio(url)

    override suspend fun generisiSifru(request: GenerisiSifruRequest): GenerisiSifruResponse = gameApi.generisiSifru(request)
    override suspend fun proveriSifruSobe(request: ProveriSifruRequest): ProveriSifruResponse = gameApi.proveriSifruSobe(request)
    override suspend fun stigaoIgrac(request: StigaoIgracRequest): StigaoIgracResponse = gameApi.stigaoIgrac(request)
    override suspend fun duel(request: DuelRequest): DuelResponse = gameApi.duel(request)
    override suspend fun cekanjeRezultata(request: CekanjeRezultataRequst): CekanjeRezultataResponse = gameApi.cekanjeRezultata(request)
    override suspend fun krajDuela(request: KrajDuelaRequest): KrajDuelaResponse = gameApi.krajDuela(request)

}