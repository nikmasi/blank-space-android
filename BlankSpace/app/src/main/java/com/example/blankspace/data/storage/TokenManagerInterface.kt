package com.example.blankspace.data.storage

interface TokenManagerInterface{
    fun saveToken(token: String)
    fun saveUserSession(username: String, password: String)
    fun getSavedUser(): Pair<String?, String?>
    fun clearSession()
}