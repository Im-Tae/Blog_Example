package com.leaf.rxandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_polling.*
import java.util.concurrent.TimeUnit

class PollingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_polling, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_polling.setOnClickListener { startPollingV1() }
        btn_polling2.setOnClickListener { startPollingV2() }
    }

    private fun startPollingV1() {
        val ob = Observable.interval(3, TimeUnit.SECONDS)
            .flatMap { o -> Observable.just("polling #1 $o") }

        ob.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s -> Log.i("log", s) }
    }

    private fun startPollingV2() {
        val ob2 = Observable.just("polling #2")
            .repeatWhen { o -> o.delay(3, TimeUnit.SECONDS) }

        ob2.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { s -> Log.i("log", s) }
    }
}
