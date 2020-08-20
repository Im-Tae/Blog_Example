package com.example.threadexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning = false

    var handler: Handler? = null

    var displayHandler: DisplayHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            textView.text = "${System.currentTimeMillis()}"
        }

        // while (true) {
        //   textView.text = "${System.currentTimeMillis()}"
        // }

        displayHandler = DisplayHandler()

        isRunning = true
        Thread1().start()

        //handler = Handler()

        // handler?.post(Thread2())
        //handler?.postDelayed(Thread2(), 1000)
    }

    inner class Thread1 : Thread() {
        override fun run() {

            while (isRunning) {

                Thread.sleep(1000)

                displayHandler?.sendEmptyMessage(0)
            }
        }
    }

    inner class Thread2 : Thread() {
        override fun run() {

            textView.text = "${System.currentTimeMillis()}"

            // handler?.post(this)
            handler?.postDelayed(Thread2(), 1000)
        }
    }

    inner class DisplayHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            textView.text = "${System.currentTimeMillis()}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}