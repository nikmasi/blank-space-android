package com.example.blankspace.data.repository.auth

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

interface AuthRepository {
    suspend fun login(credentials: LoginRequest): LoginResponse
    suspend fun postRegistracija(request: RegistracijaRequest): RegistracijaResponse

    suspend fun postZaboravljenaLozinka(request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse

    suspend fun postZaboravljenaLozinkaPitanje(request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse

    suspend fun postNovaLozinka(request: NovaLozinkaRequest): NovaLozinkaResponse

}