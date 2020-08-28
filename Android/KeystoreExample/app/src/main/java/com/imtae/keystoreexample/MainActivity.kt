package com.imtae.keystoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imtae.keystoreexample.BuildConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BuildConfig.BASE_URL
    }
}