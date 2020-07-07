package com.leaf.rxandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.fragment_on_click.*

class OnClickFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_on_click, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getClickEventObservable()
            .map { _ -> "clicked" }
            .subscribe(getObserver())

        getClickEventObservableExtra()
            .map { _ -> 7 }
            .flatMap(this::compareNumbers)
            .subscribe { s -> Log.i("log", s) }
    }

    private fun getClickEventObservable() : Observable<View> {
        return Observable.create { e -> btn_click_observer.setOnClickListener(e::onNext) }
    }

    private fun getClickEventObservableExtra(): Observable<View> {
        return Observable.create { s -> btn_click_observer_extra.setOnClickListener(s::onNext) }
    }

    private fun compareNumbers(input : Int): Observable<String> {
        return Observable.just(input)
            .flatMap { i ->
                val data = (1..10).random()
                return@flatMap Observable.just("local : $i", "remote : $data", "result = ", (i == data).toString())
            }
    }

    private fun getObserver(): DisposableObserver<String> {
        return object : DisposableObserver<String>() {

            override fun onNext(s: String) { Log.i("log", s) }

            override fun onError(e: Throwable) { Log.i("log", e.message.toString()) }

            override fun onComplete() { Log.i("log", "complete") }
        }
    }
}
