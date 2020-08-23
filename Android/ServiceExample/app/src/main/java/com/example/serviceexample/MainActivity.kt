package com.example.serviceexample

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var serviceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_service.setOnClickListener {
            serviceIntent = Intent(this, MyService1::class.java)
            startService(serviceIntent)
        }

        stop_service.setOnClickListener {
            stopService(serviceIntent)
        }

        start_intent_service.setOnClickListener {
            serviceIntent = Intent(this, MyIntentService::class.java)
            startService(serviceIntent)
        }

        start_foreground_service.setOnClickListener {

            serviceIntent = Intent(this, MyService2::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }
    }
}