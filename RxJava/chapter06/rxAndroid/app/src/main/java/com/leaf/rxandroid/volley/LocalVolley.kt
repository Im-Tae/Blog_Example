package com.leaf.rxandroid.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.lang.IllegalStateException

class LocalVolley {


    companion object {

        private lateinit var sRequestQueue : RequestQueue

        fun init(context: Context) {
            sRequestQueue = Volley.newRequestQueue(context)
        }

        @Throws(IllegalStateException::class)
        fun getRequestQueue(): RequestQueue = sRequestQueue
    }
}