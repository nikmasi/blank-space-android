package com.example.blankspace.data

import com.example.blankspace.data.retrofit.GameApi
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