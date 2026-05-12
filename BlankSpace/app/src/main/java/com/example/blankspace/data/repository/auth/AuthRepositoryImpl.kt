package com.example.blankspace.data.repository.auth

import com.example.blankspace.data.retrofit.api.AuthApi
import com.example.blankspace.data.retrofit.data.LoginRequest
import com.example.blankspace.data.retrofit.data.NovaLozinkaRequest
import com.example.blankspace.data.retrofit.data.NovaLozinkaResponse
import com.example.blankspace.data.retrofit.data.RegistracijaRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaPitanjeResponse
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaRequest
import com.example.blankspace.data.retrofit.data.ZaboravljenaLozinkaResponse
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