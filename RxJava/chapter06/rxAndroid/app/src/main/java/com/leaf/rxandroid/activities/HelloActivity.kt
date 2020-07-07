package com.leaf.rxandroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main2.*

class HelloActivity : AppCompatActivity() {

    lateinit var mDisposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val observer = object : DisposableObserver<String>() {
//            override fun onNext(s: String) {
//                textView.text = s
//            }
//
//            override fun onError(e: Throwable) {}
//
//            override fun onComplete() {}
//        }
//
//        Observable.create(ObservableOnSubscribe<String> { emitter ->
//            emitter.onNext("hello world!")
//            emitter.onComplete()
//        }).subscribe(observer)

        val observer = object : DisposableObserver<String>() {
            override fun onNext(s: String) {
                textView.text = s
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }

        mDisposable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("hello world!")
            emitter.onComplete()
        }).subscribeWith(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }
}
