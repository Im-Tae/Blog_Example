package com.example.serviceexample

import android.app.IntentService
import android.content.Intent
import android.util.Log

class MyIntentService : IntentService("MyIntentService") {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MyIntentService", "서비스 실행")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MyIntentService", "서비스 종료")
    }

    override fun onHandleIntent(intent: Intent?) {

        for (i in 0..10) {
            Thread.sleep(1000)
            Log.i("MyIntentService", "MyIntentService : $i")
        }
    }
}
