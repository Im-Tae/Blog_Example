package com.leaf.rxandroid.activities

import android.os.Bundle
import com.leaf.rxandroid.R
import com.leaf.rxandroid.fragments.RecyclerViewFragment
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class HelloActivityV3 : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.just("Hello, rx world!")
            .compose(bindToLifecycle())
            .subscribe { s -> textView.text = s }
    }
}
