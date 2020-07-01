package com.example.lottielibraryexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 이미지 실행 코드
        //loading_image.playAnimation()

        // 반복재생
        //loading_image.loop(true)

        loading_image.addAnimatorUpdateListener {
            if (loading_image.isAnimating) {
                startActivity(Intent(this, MainActivity2::class.java))
            }
        }
    }
}