package com.example.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {

            val builder = createNotificationChannel("id1", "name1")
                .setTicker("Ticker 1")
                .setSmallIcon(android.R.drawable.ic_menu_search)
                .setNumber(100)
                .setAutoCancel(true)
                .setContentTitle("Content Title 1")
                .setContentText("Content Text 1")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        }

        button2.setOnClickListener {

            val intent = Intent(this, MainActivity2::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val builder = createNotificationChannel("id2", "name2")
                .setTicker("Ticker 2")
                .setSmallIcon(android.R.drawable.ic_menu_search)
                .setNumber(100)
                .setAutoCancel(true)
                .setContentTitle("Content Title 2")
                .setContentText("Content Text 2")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            with(NotificationManagerCompat.from(this)) {
                notify(1, builder.build())
            }
        }

        button3.setOnClickListener {

            val text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

            val style = NotificationCompat.BigTextStyle().bigText(text)

            val builder = createNotificationChannel("id3", "name3")
                .setTicker("Ticker 3")
                .setSmallIcon(android.R.drawable.ic_menu_search)
                .setNumber(100)
                .setAutoCancel(true)
                .setContentTitle("Content Title 3")
                .setContentText("Content Text 3")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(style)

            with(NotificationManagerCompat.from(this)) {
                notify(3, builder.build())
            }
        }

        button4.setOnClickListener {

            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.spagetti)

            val style = NotificationCompat.BigPictureStyle().bigPicture(bitmap)

            val builder = createNotificationChannel("id4", "name4")
                .setTicker("Ticker 4")
                .setSmallIcon(android.R.drawable.ic_menu_search)
                .setNumber(100)
                .setAutoCancel(true)
                .setContentTitle("Content Title 4")
                .setContentText("Content Text 4")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(style)

            with(NotificationManagerCompat.from(this)) {
                notify(4, builder.build())
            }
        }

        button5.setOnClickListener {

            val style = NotificationCompat.InboxStyle()

            style.addLine("first")
            style.addLine("second")
            style.addLine("third")
            style.addLine("four")
            style.addLine("five")
            style.addLine("six")
            style.addLine("seven")

            val builder = createNotificationChannel("id5", "name5")
                .setTicker("Ticker 5")
                .setSmallIcon(android.R.drawable.ic_menu_search)
                .setNumber(100)
                .setAutoCancel(true)
                .setContentTitle("Content Title 5")
                .setContentText("Content Text 5")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(style)

            with(NotificationManagerCompat.from(this)) {
                notify(5, builder.build())
            }
        }
    }

    private fun createNotificationChannel(id :String, name :String) : NotificationCompat.Builder{

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(channel)

            NotificationCompat.Builder(this, id)

        } else {
            NotificationCompat.Builder(this)
        }
    }
}