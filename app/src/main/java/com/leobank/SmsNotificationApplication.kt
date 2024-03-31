package com.leobank

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class SmsNotificationApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            "sms_reminder",
            "Sms reminder channel",
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = "A notification channel for sms reminders"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}