package com.example.ipcexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class IPCService : Service() {

    var value = 0
    var thread: ThreadClass? = null

    var binder: IBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        thread = ThreadClass()
        thread?.start()

        return super.onStartCommand(intent, flags, startId)
    }

    inner class ThreadClass : Thread() {

        override fun run() {

            while (true) {

                Thread.sleep(1000)

                Log.d("test", "$value")
                value++
            }
        }
    }

    inner class LocalBinder: Binder() {

        fun getService(): IPCService = this@IPCService
    }

    fun getNumber(): Int = value
}
