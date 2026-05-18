package com.example.blankspace.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.blankspace.data.repository.room.MyRoomRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: MyRoomRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("AppInit", "Try sync")
            repository.syncAllData()

            Log.d("AppInit", "Sync data to work manager")

            applicationContext
                .getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("has_downloaded_data", true)
                .apply()

            Result.success()

        } catch (e: Exception) {
            Log.e("AppInit", "Worker failed", e)
            Result.retry()
        }
    }
}