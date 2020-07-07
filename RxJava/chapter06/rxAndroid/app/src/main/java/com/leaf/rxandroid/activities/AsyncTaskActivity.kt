package com.leaf.rxandroid.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.leaf.rxandroid.R
import io.reactivex.MaybeObserver
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class AsyncTaskActivity : AppCompatActivity() {

    private lateinit var myAsyncTask : MyAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAndroidAsync()
        initRxAsync()
    }

    private fun initAndroidAsync() {
        myAsyncTask = MyAsyncTask()
        myAsyncTask.execute("Hello", "async", "world")
    }

    inner class MyAsyncTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            val word = StringBuilder()

            for (s in params) {
                word.append(s).append(" ")
            }

            return word.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            textView.text = result
        }
    }

    private fun initRxAsync() {
        Observable.just("Hello", "rx", "world")
            .reduce { x: String, y: String -> "$x $y" }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun getObserver() : MaybeObserver<String> {
        return object : MaybeObserver<String> {

            override fun onSubscribe(d: Disposable) {}

            override fun onSuccess(result: String) {
                textView2.text = result
            }

            override fun onError(e: Throwable) {
                Log.e("log", e.message.toString())
            }

            override fun onComplete() {
                Log.i("log", "done")
            }
        }
    }
}