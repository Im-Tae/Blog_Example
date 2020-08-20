package com.example.threadexample

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_async_task.*

class AsyncTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)

        AsyncTaskClass().execute(10, 20)
    }

    inner class AsyncTaskClass : AsyncTask<Int, Long, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Int?): String {

            Log.i("params", "${params[0]}, ${params[1]}")

            while (true) {
                Thread.sleep(1000)
                publishProgress(System.currentTimeMillis())
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: Long?) {
            super.onProgressUpdate(*values)

            asyncTask_textView.text = values[0].toString()
        }
    }
}