package com.rxyzent.remainder.core.helper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.widget.RemoteViews
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.rxyzent.remainder.R
import com.rxyzent.remainder.ui.main.MainActivity

class MyBroadcastReciver:BroadcastReceiver() {

    private val CHANNEL_ID="channel1"
    private val notificationId=1

    override fun onReceive(p0: Context?, p1: Intent?) {
        val data = p1?.extras
        var title = "Title"
        var description = "Description"
        var type = "todo"
        data?.let {
             title = it.getString("title")!!
             description = it.getString("description")!!
             type = it.getString("type")!!
        }

        var icon = R.drawable.ic_baseline_sticky_note_2_24

        when (type) {
            "todo" -> {
                icon = R.drawable.todo
            }
            "payment" -> {
                icon = R.drawable.payment
            }
            "birthday" -> {
                icon = R.drawable.cake
            }
        }


        val packageName = p0?.packageName
        val alarmSong = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(p0, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(p0, 0, intent, 0)

//        val contentView = RemoteViews(packageName,R.layout.notification_layout)
//        contentView.setTextViewText(R.id.notifTitle,title)
//        contentView.setTextViewText(R.id.notifDescription,description)
//        contentView.setImageViewResource(R.id.notifIcon,icon)


        val builder = p0?.let {
            NotificationCompat.Builder(it, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_campaign_24)
                .setLargeIcon(BitmapFactory.decodeResource(p0.resources,icon))
                .setContentTitle(title)
                .setContentText(description)
//                .setSubText("sub text")
//                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(alarmSong)
                .addAction(0,"open",pendingIntent)
                .setAutoCancel(true)
        }

        //create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "R.string.channel_name"
            val descriptionText = "R.string.channel_description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            channel.setShowBadge(true)

            if (builder != null) {
                with(p0?.let { NotificationManagerCompat.from(it) }) {
                    // notificationId is a unique int for each notification that you must define
                    this?.notify(notificationId, builder.build())
                }
            }
            val notificationManager: NotificationManager =
                p0?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


        }

    }

}