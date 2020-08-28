package com.imtae.securitylibraryexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = PreferenceManager(this)

        button.setOnClickListener {
            pref.setData("data", editText.text.toString())
            textView.text = editText.text.toString()
        }

        textView.text = pref.getData("data") ?: ""
    }
}