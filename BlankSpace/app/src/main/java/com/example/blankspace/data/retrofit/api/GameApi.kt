package com.example.blankspace.data.retrofit.api

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
import retrofit2.http.Body
import retrofit2.http.POST

interface GameApi {

    // igra sam
    @POST("igra_sam_android/")
    suspend fun getIgraSamData(@Body request: IgraSamRequest): IgraSamResponse

    @POST("kraj_igre_adndroid/")
    suspend fun krajIgre(@Body request: KrajIgreRequest): KrajIgreResponse

    @POST("get_audio/")
    suspend fun getAudio(@Body url:AudioRequest):AudioResponse

    // duel

    @POST("generisi_sifru_sobe_android/")
    suspend fun generisiSifru(@Body request: GenerisiSifruRequest): GenerisiSifruResponse

    @POST("proveri_sifru_sobe_android/")
    suspend fun proveriSifruSobe(@Body request: ProveriSifruRequest): ProveriSifruResponse

    @POST("stigao_igrac_android/")
    suspend fun stigaoIgrac(@Body request: StigaoIgracRequest): StigaoIgracResponse

    @POST("duel_android/")
    suspend fun duel(@Body request: DuelRequest): DuelResponse

    @POST("cekanje_rezultata_android/")
    suspend fun cekanjeRezultata(@Body request: CekanjeRezultataRequst):CekanjeRezultataResponse

    @POST("kraj_duela_android/")
    suspend fun krajDuela(@Body request: KrajDuelaRequest):KrajDuelaResponse


}
