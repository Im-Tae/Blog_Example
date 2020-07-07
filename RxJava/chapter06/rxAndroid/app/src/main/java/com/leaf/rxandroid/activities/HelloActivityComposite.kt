package com.leaf.rxandroid.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main2.*

class HelloActivityComposite : AppCompatActivity() {

    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable = Observable.create(ObservableOnSubscribe<String> { emitter ->
            emitter.onNext("hello world!")
            emitter.onComplete()
        }).subscribe { s -> textView.text = s }

        mCompositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.dispose()
    }
}