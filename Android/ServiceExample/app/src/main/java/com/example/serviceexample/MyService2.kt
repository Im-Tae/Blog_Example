package com.example.serviceexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class MyService2 : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val builder = createNotificationChannel("MyService2", "service")
            .setTicker("MyService2 서비스")
            .setContentTitle("MyService2 서비스 시작")
            .setContentText("MyService2 서비스 동작 중입니다.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)


        startForeground(1, builder.build())

        Log.i("MyService2", "서비스 실행")
        ThreadClass().start()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel(id :String, name :String) : NotificationCompat.Builder {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(channel)

            NotificationCompat.Builder(this, id)
        } else {
            NotificationCompat.Builder(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyService2", "서비스 종료")
    }

    inner class ThreadClass : Thread() {

        override fun run() {

            for (i in 0..10) {
                Thread.sleep(1000)
                Log.i("MyService2", "Foreground 서비스 : $i")
            }
        }
    }
}
