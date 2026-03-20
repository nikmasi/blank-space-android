package com.example.blankspace.data

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

interface AuthRepository {
    suspend fun login(credentials: LoginRequest): LoginResponse
    suspend fun postRegistracija(request: RegistracijaRequest): RegistracijaResponse

    suspend fun postZaboravljenaLozinka(request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse

    suspend fun postZaboravljenaLozinkaPitanje(request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse

    suspend fun postNovaLozinka(request: NovaLozinkaRequest): NovaLozinkaResponse

}