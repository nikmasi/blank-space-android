package com.example.blankspace.data

import com.example.blankspace.data.retrofit.AuthApi
import com.example.blankspace.data.retrofit.models.LoginRequest
import com.example.blankspace.data.retrofit.models.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.models.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.models.RegistracijaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.models.ZaboravljenaLozinkaResponse
import jakarta.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(credentials: LoginRequest) = authApi.login(credentials)
    override suspend fun postRegistracija(request: RegistracijaRequest) = authApi.postRegistracija(request)

    override suspend fun postZaboravljenaLozinka(request: ZaboravljenaLozinkaRequest): ZaboravljenaLozinkaResponse = authApi.postZaboravljenaLozinka(request)
    override suspend fun postZaboravljenaLozinkaPitanje(request: ZaboravljenaLozinkaPitanjeRequest): ZaboravljenaLozinkaPitanjeResponse = authApi.postZaboravljenaLozinkaPitanje(request)
    override suspend fun postNovaLozinka(request: NovaLozinkaRequest): NovaLozinkaResponse = authApi.postNovaLozinka(request)
}