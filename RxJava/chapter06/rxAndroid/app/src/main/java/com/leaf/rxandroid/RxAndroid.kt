package com.leaf.rxandroid

import android.app.Application
import com.leaf.rxandroid.volley.LocalVolley

class RxAndroid : Application() {
    override fun onCreate() {
        super.onCreate()

        LocalVolley.init(applicationContext)
    }
}