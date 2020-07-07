package com.leaf.rxandroid.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.leaf.rxandroid.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_loop.*

class LoopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loop)

        val samples: Iterable<String> = listOf("banana", "orange", "apple", "apple mango", "melon", "watermelon")

        // kotlin
        btn_loop.setOnClickListener {
            Log.i("log", ">>>>> get an apple :: kotlin")

            for (s in samples) {
                if (s.contains("apple")) {
                    Log.i("log", s)
                    break
                }
            }
        }

        // RxJava 2.x
        btn_loop2.setOnClickListener {
            Log.i("log", ">>>>> get an apple :: RxJava 2.x")

            Observable.fromIterable(samples)
                .filter { s -> s.contains("apple") }
                //.skipWhile { s -> !s.contains("apple") }
                .first("Not found")
                .subscribe { s -> Log.i("log", s) }
        }
    }
}
