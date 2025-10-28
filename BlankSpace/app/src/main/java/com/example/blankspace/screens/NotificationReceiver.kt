package com.example.blankspace.screens

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.blankspace.screens.profil_rang_pravila.getNotificationTime
import com.example.blankspace.screens.profil_rang_pravila.scheduleDailyNotification

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission", "NotificationPermission")
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = NotificationManagerCompat.from(context)
        val builder = NotificationCompat.Builder(context, "daily_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("BlankSpace 🎵")
            .setContentText("Vreme je za igru!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // Kanal za Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "daily_channel",
                "Dnevne notifikacije",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1, builder.build())

        val time = getNotificationTime(context)
        if (time != null) {
            // Ponovno zakazivanje za sutra, koristeći setExactAndAllowWhileIdle()
            scheduleDailyNotification(context, time.first, time.second)
        }
    }
}
