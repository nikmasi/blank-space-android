package com.example.blankspace.data.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.inject.Inject

class TokenManager @Inject constructor(
    context: Context
) : TokenManagerInterface {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString("access_token", token)
            .apply()
    }

    override fun saveUserSession(username: String, password: String) {
        sharedPreferences.edit()
            .putString("user_key", username)
            .putString("password_key", password)
            .apply()
    }

    override fun getSavedUser(): Pair<String?, String?> {
        val user = sharedPreferences.getString("user_key", null)
        val pass = sharedPreferences.getString("password_key", null)
        return Pair(user, pass)
    }

    override fun clearSession() {
        sharedPreferences.edit().remove("user_key").remove("password_key").apply()
    }
}
