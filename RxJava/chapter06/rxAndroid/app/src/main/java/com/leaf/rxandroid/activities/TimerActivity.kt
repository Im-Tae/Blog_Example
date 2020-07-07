package com.leaf.rxandroid.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.leaf.rxandroid.R
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class TimerActivity : AppCompatActivity() {

    private var count = 0

    // TimerTask

    private val DELAY = 0L
    private val PERIOD = 1000L
    private val mTimer = Timer()

    private fun timerStart() {
        var count = 0
        mTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                textView.text = count++.toString()
            }

        }, DELAY, PERIOD)
    }

    private fun timerStop() = mTimer.cancel()


    // CountDownTimer

    private val MILLISINFUTURE = 11 * 1000L
    private val COUNT_DOWN_INTERVAL = 1000L
    lateinit var mCountDownTimer : CountDownTimer

    private fun initCountDownTask() {
        mCountDownTimer = object : CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {

            override fun onTick(millisUntilFinished: Long) {
                textView.text = count--.toString()
            }

            override fun onFinish() {
                textView.text = "Finish ."
            }
        }
    }

    private fun countDownTimerStart() {
        count = 10
        mCountDownTimer.start()
    }

    private fun countDownTimerStop() = mCountDownTimer.cancel()


    //Handler

    private lateinit var mHandler : Handler

    private val timer = object : Runnable {
        override fun run() {
            textView.text = count++.toString()
            mHandler.postDelayed(this, 1000)
        }
    }

    private fun initHandler() { mHandler = Handler() }

    private fun handlerStart() {
        count = 0
        mHandler.postDelayed(timer, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button.setOnClickListener { timerStart() }

        button2.setOnClickListener { initCountDownTask(); countDownTimerStart() }

        button3.setOnClickListener { initHandler(); handlerStart() }

    }
}