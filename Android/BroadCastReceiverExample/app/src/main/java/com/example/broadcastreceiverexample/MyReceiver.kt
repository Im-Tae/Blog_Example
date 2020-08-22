package com.example.broadcastreceiverexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val data = intent.getIntExtra("data", 0)

        Toast.makeText(context, "동작 중 $data", Toast.LENGTH_SHORT).show()
    }
}
