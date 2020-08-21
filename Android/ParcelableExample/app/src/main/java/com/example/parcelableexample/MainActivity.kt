package com.example.parcelableexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            val data = ParcelableClass()
            data.number = 7
            data.name = "ImLeaf"

            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("data", data)
            startActivity(intent)
        }
    }
}