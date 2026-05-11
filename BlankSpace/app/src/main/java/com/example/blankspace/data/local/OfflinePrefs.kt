package com.example.blankspace.data.local

import android.content.Context

fun hasDownloadedData(context: Context): Boolean {
    val prefs = context.getSharedPreferences("offline_mode", Context.MODE_PRIVATE)
    return prefs.getBoolean("data_downloaded", false)
}

fun setDownloadedFlag(context: Context) {
    val prefs = context.getSharedPreferences("offline_mode", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("data_downloaded", true).apply()
}