package com.example.blankspace.data.retrofit.api

import com.example.blankspace.data.retrofit.data.LoginRequest
import com.example.blankspace.data.retrofit.data.LoginResponse
import com.example.blankspace.data.retrofit.data.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.data.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.data.RegistracijaRequest
import com.example.blankspace.data.retrofit.data.RegistracijaResponse
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaResponse
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
