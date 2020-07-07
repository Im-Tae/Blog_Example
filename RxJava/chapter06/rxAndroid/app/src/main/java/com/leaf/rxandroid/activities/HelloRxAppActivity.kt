package com.leaf.rxandroid.activities

import android.os.Bundle
import com.leaf.rxandroid.R
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_main2.*

class HelloRxAppActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("Hello world!")
            emitter.onComplete()
        })
            //.compose(bindToLifecycle())
            .compose(bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe { s -> textView.text = s }
    }
}