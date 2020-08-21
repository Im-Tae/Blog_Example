package com.example.parcelableexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val data = intent.getParcelableExtra<ParcelableClass>("data")

        number.text = data?.number.toString()
        name.text = data?.name
    }
}