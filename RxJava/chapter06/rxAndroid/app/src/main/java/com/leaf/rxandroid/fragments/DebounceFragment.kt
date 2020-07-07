package com.leaf.rxandroid.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_debounce.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DebounceFragment : Fragment() {

    lateinit var mDisposable : Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_debounce, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDisposable = getObservable()
            .debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                val sdf = SimpleDateFormat("HH:mm:ss", Locale.KOREA)
                val time = sdf.format(Calendar.getInstance().time)
                tv_display.text = "Clicked : $time"
            }
    }

    private fun getObservable() : Observable<View> {
        return Observable.create { e -> btn_debounce.setOnClickListener(e::onNext) }
    }

}
