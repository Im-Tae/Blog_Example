package com.example.serviceexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService1 : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("MyService1", "서비스 실행")
        ThreadClass().start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyService1", "서비스 종료")
    }

    inner class ThreadClass : Thread() {

        override fun run() {

            for (i in 0..10) {
                Thread.sleep(1000)
                Log.i("MyService1", "MyService1 : $i")
            }
        }
    }
}
