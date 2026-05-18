package com.example.blankspace.hilt

import com.example.blankspace.data.storage.TokenManager
import com.example.blankspace.data.storage.TokenManagerInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    abstract fun bindTokenManager(impl: TokenManager): TokenManagerInterface
}