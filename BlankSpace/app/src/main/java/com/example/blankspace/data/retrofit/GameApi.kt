package com.example.blankspace.data.retrofit

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
