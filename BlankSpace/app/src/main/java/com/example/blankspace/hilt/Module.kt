package com.example.blankspace.hilt

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.blankspace.data.AdminRepository
import com.example.blankspace.data.AdminRepositoryImpl
import com.example.blankspace.data.AuthRepository
import com.example.blankspace.data.AuthRepositoryImpl
import com.example.blankspace.data.ContentRepository
import com.example.blankspace.data.ContentRepositoryImpl
import com.example.blankspace.data.GameRepository
import com.example.blankspace.data.GameRepositoryImpl
import com.example.blankspace.data.Repository
import com.example.blankspace.data.RepositoryInterface
import com.example.blankspace.data.SuggestionRepository
import com.example.blankspace.data.SuggestionRepositoryImpl
import com.example.blankspace.data.retrofit.AdminApi
import com.example.blankspace.data.retrofit.Api
import com.example.blankspace.data.retrofit.AuthApi
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.data.retrofit.ContentApi
import com.example.blankspace.data.retrofit.GameApi
import com.example.blankspace.data.retrofit.SuggestionApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            // Logika: Ako je login poziv, dodaj token (ili obrnuto zavisno od tvog API-ja)
            if (request.url.toString().contains("login")) {
                val token = getTokenFromSharedPrefs(context)
                val newRequest = request.newBuilder().apply {
                    if (!token.isNullOrEmpty()) {
                        header("Authorization", "Bearer $token")
                    }
                }.build()
                chain.proceed(newRequest)
            } else {
                chain.proceed(request)
            }
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGameApi(retrofit: Retrofit): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAdminApi(retrofit: Retrofit): AdminApi {
        return retrofit.create(AdminApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContentApi(retrofit: Retrofit): ContentApi {
        return retrofit.create(ContentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSuggestionApi(retrofit: Retrofit): SuggestionApi {
        return retrofit.create(SuggestionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImpressionsApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    private fun getTokenFromSharedPrefs(context: Context): String? {
        val sharedPreferences = EncryptedSharedPreferences.create(
            "auth_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context, // Koristimo injectovani Context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences.getString("access_token", null)
    }

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(
        repositoryImpl: Repository
    ): RepositoryInterface

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