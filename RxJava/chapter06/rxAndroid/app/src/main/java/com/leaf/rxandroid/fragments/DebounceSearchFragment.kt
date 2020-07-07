package com.leaf.rxandroid.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_debounce_search.*
import java.util.concurrent.TimeUnit

class DebounceSearchFragment : Fragment() {

    lateinit var mDisposable : Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_debounce_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDisposable = getObservable()
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter { s -> !TextUtils.isEmpty(s) }
            .subscribeOn(Schedulers.newThread())
            .subscribeWith(getObserver())
    }

    private fun getObservable() : Observable<CharSequence> {
        return Observable.create { e -> dsf_input_deb_search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = e.onNext(s.toString())

            override fun afterTextChanged(s: Editable) {}

        }) }
    }

    private fun getObserver() : DisposableObserver<CharSequence> {
        return object : DisposableObserver<CharSequence>() {

            override fun onNext(word: CharSequence) { Log.i("log", word.toString()) }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}

        }
    }
}
