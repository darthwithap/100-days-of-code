package com.darthwithap.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "firstChannel", "Default", NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            val headsUpChannel = NotificationChannel(
                "secondChannel", "Second", NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(headsUpChannel)
        }

        btnNoti1.setOnClickListener {
            val simpleNotification = NotificationCompat.Builder(
                this, "firstChannel"
            )
                .setContentTitle("Simple title")
                .setContentText("This is a v v v v simple notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()

            notificationManager.notify(1, simpleNotification)
        }

        btnClickableNoti.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("https://google.com")

            val pendingIntent = PendingIntent.getActivity(
                this@MainActivity,
                202,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            val clickableNoti = NotificationCompat.Builder(
                this, "firstChannel"
            )
                .setContentTitle("Simple Title 2")
                .setAutoCancel(true)
                .setContentText("Again this is a v v v simple notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build()

            notificationManager.notify(2, clickableNoti)
        }

        btnNotiAction.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = Uri.parse("https://google.com")

            val pendingIntent = PendingIntent.getActivity(this@MainActivity, 303, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            val actionNoti = NotificationCompat.Builder(
                this, "firstChannel")
                .setAutoCancel(true)
                .setContentTitle("Action notification")
                .setContentText("Action notification context buddy!!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, "open browser", pendingIntent)
                .build()

            notificationManager.notify(3, actionNoti)
        }

        btnHeadUpNoti.setOnClickListener {
            val headUpNoti = NotificationCompat.Builder(
                this, "secondChannel")
                .setAutoCancel(true)
                .setContentTitle("Action notification")
                .setContentText("Action notification context buddy!!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(4, headUpNoti)
        }

    }
}