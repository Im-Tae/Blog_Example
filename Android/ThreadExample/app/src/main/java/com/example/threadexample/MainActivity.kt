package com.example.threadexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            textView.text = "${System.currentTimeMillis()}"
        }

//        while (true) {
//            textView.text = "${System.currentTimeMillis()}"
//        }

        isRunning = true
        Thread1().start()
    }

    inner class Thread1 : Thread() {
        override fun run() {

            while (isRunning) {

                Thread.sleep(1000)

                runOnUiThread {
                    textView.text = "${System.currentTimeMillis()}"
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
    }
}