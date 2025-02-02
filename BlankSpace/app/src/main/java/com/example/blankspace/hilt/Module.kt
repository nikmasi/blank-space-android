package com.example.blankspace.hilt

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.blankspace.data.retrofit.Api
import com.example.blankspace.data.retrofit.BASE_URL
import com.example.blankspace.data.retrofit.BASE_URL_LOCALHOST
import com.google.gson.GsonBuilder
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

    @Singleton
    @Provides
    fun provideImpressionsApi(@ApplicationContext context: Context): Api {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            // Ako je to login poziv, dodaj Authorization header sa tokenom
            if (request.url.toString().contains("login")) {
                val token = getTokenFromSharedPrefs(context) // Dohvati token iz SharedPreferences
                val newRequest = request.newBuilder().apply {
                    if (!token.isNullOrEmpty()) {
                        header("Authorization", "Bearer $token")
                    }
                }.build()
                chain.proceed(newRequest)
            } else {
                // Za sve ostale API pozive, ne dodaj header
                chain.proceed(request)
            }
        }

        // OkHttpClient sa logovanjem i dodatim Interceptor-om za login
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor) // Za logovanje
            addInterceptor(authInterceptor) // Dodaj interceptor samo za login
        }.build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



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
