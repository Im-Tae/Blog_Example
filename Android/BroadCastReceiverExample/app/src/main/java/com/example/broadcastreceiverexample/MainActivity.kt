package com.example.broadcastreceiverexample

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var myReceiver: MyReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addReceiver()

        button.setOnClickListener {

            val intent = Intent(this, MyReceiver::class.java)

            sendBroadcast(intent)
        }
    }

    private fun addReceiver() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

            myReceiver = MyReceiver()

            val filter = IntentFilter("com.example.myreceiver")
            registerReceiver(myReceiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (myReceiver != null) {
            unregisterReceiver(myReceiver)
            myReceiver = null
        }
    }
}