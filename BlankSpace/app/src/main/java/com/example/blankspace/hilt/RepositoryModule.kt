package com.example.blankspace.hilt

import com.example.blankspace.data.repository.admin.AdminRepository
import com.example.blankspace.data.repository.admin.AdminRepositoryImpl
import com.example.blankspace.data.repository.auth.AuthRepository
import com.example.blankspace.data.repository.auth.AuthRepositoryImpl
import com.example.blankspace.data.repository.content.ContentRepository
import com.example.blankspace.data.repository.content.ContentRepositoryImpl
import com.example.blankspace.data.repository.game.GameRepository
import com.example.blankspace.data.repository.game.GameRepositoryImpl
import com.example.blankspace.data.repository.suggestion.SuggestionRepository
import com.example.blankspace.data.repository.suggestion.SuggestionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGameRepository(impl: GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    abstract fun bindAdminRepository(impl: AdminRepositoryImpl): AdminRepository


    @Binds
    @Singleton
    abstract fun bindContentRepository(impl: ContentRepositoryImpl): ContentRepository

    @Binds
    @Singleton
    abstract fun bindSuggestionRepository(impl: SuggestionRepositoryImpl): SuggestionRepository
}