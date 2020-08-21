package com.example.ipcexample

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ipcService : IPCService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, IPCService::class.java)

        if(!checkServiceRunning("com.example.ipcexample.IPCService")) {

            startService(intent)
        }

        bindService(intent, connection, Context.BIND_AUTO_CREATE)

        button.setOnClickListener {
            textView.text = "${ipcService?.getNumber()}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    private fun checkServiceRunning(serviceName: String): Boolean {

        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service : ActivityManager.RunningServiceInfo in manager.getRunningServices(Int.MAX_VALUE)) {
            if (service.service.className == serviceName)
                return true
        }

        return false
    }

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            val binder = service as IPCService.LocalBinder

            ipcService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {

            ipcService = null
        }
    }
}