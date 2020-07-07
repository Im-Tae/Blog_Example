package com.leaf.rxandroid.activities

import android.app.Activity
import android.os.Bundle
import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*


class HelloActivityV1 : Activity() {

    private lateinit var mDisposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observer : DisposableObserver<String> = object: DisposableObserver<String>() {

            override fun onNext(s: String) { textView.text = s }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        }

        mDisposable = Observable.create(ObservableOnSubscribe<String> { e ->
            e.onNext("Hello World!")
            e.onComplete()
        }).subscribeWith(observer)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (!mDisposable.isDisposed) mDisposable.dispose()
    }
}