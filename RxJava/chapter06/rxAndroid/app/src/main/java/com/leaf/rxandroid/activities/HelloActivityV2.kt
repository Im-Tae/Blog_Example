package com.leaf.rxandroid.activities

import android.app.Activity
import android.os.Bundle
import com.leaf.rxandroid.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class HelloActivityV2 : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.create<String> { s ->
            s.onNext("Hello world!")
            s.onComplete()
        }.subscribe { t -> textView.text = t }

        // just 함수 사용
//        Observable.just("Hello world!")
//            .subscribe { t -> textView.text = t }
    }
}