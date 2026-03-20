package com.example.blankspace.data.retrofit

import com.example.blankspace.data.retrofit.models.LoginRequest
import com.example.blankspace.data.retrofit.models.LoginResponse
import com.example.blankspace.data.retrofit.models.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.models.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.RegistracijaResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login_android/")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("registracija_android/")
    suspend fun postRegistracija(@Body request: RegistracijaRequest): RegistracijaResponse

    @POST("zaboravljena_lozinka_android/")
    suspend fun postZaboravljenaLozinka(@Body request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse

    @POST("zaboravljena_lozinka_pitanje_android/")
    suspend fun postZaboravljenaLozinkaPitanje(@Body request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse

    @POST("nova_lozinka_android/")
    suspend fun postNovaLozinka(@Body request: NovaLozinkaRequest): NovaLozinkaResponse
}
