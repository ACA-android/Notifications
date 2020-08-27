package com.dm.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()
        createNotificationChannel2()

        notifBtn.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {

        val intent = Intent(this, NotificationAvtivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notif = createNotification(pendingIntent)

        NotificationManagerCompat.from(this).notify(42, notif)
        NotificationManagerCompat.from(this).notify(423, createImageNotification(pendingIntent))
        NotificationManagerCompat.from(this).notify(424, createCustomNotification(pendingIntent))
    }

    private val CHANNEL_ID = "notif1"
    private val CHANNEL_ID2 = "notif2"

    private fun createNotification(pendingIntent: PendingIntent): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID2)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Hello notification")
            .setContentText("I am a notification hbdjsakp fiuadsop fiopads hiofddasp hfiopads hifopas hif adspoihdhsfaahjksdaf")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setOngoing(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("I am a notification hbdjsakp fiuadsop fdshuaiof hyusia hufiasd huifo hausif husai hfuiso ahfuis dhuif hauidsahufio sdaoifhu sdifhosahufiuao fuhdio fiopads hiofddasp hfiopads hifopas hif adspoihdhsfaahjksdaf")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, "reply", pendingIntent)
            .addAction(0, "snooze", pendingIntent)
        return builder.build()
    }

    private fun createImageNotification(pendingIntent: PendingIntent): Notification {
        val image = BitmapFactory.decodeResource(resources, R.drawable.index)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Image saved")
            .setLargeIcon(image)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    private fun createCustomNotification(pendingIntent: PendingIntent): Notification {
        val customLayout = RemoteViews("com.dm.notifications", R.layout.notidication)
        val smallLayout = RemoteViews("com.dm.notifications", R.layout.notidication_small)

        customLayout.setOnClickPendingIntent(R.id.button1, pendingIntent)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(smallLayout)
            .setCustomBigContentView(customLayout)
        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "First channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "this is out first channel"
                setBypassDnd(true)
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotificationChannel2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID2,
                "second channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "whis is a low importance channel"
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}