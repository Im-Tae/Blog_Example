package com.leaf.rxandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.leaf.rxandroid.R
import com.leaf.rxandroid.square.Contributor
import com.leaf.rxandroid.square.RestfulAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_ok_http.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OkHttpFragment : Fragment() {

    private val sName = "Im-Tae"
    private val sRepo = "RxJava2_Study"
    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ok_http, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ohf_btn_retrofit.setOnClickListener { startOkHttp() }

        ohf_btn_get_retrofit_okhttp.setOnClickListener { startRetrofit() }

        ohf_btn_get_retrofit_okhttp_rx.setOnClickListener { startRx() }
    }

    // retrofit + okHttp
    private fun startOkHttp() {
        val service = RestfulAdapter.getServiceApi()
        val call = service.getCallContributors(sName, sRepo)

        call.enqueue(object : Callback<List<Contributor>> {
            override fun onResponse(call: Call<List<Contributor>>, response: Response<List<Contributor>>) {
                if (response.isSuccessful) {
                    val contributors = response.body()

                    for (c in contributors!!) {
                        Log.i("log", c.toString())
                    }
                } else {
                    Log.i("log", "not successful")
                }
            }

            override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
                Log.i("log", t.message.toString())
            }
        })
    }

    // retrofit + okHttp( Call의 내부 )
    private fun startRetrofit() {
        val service = RestfulAdapter.getSimpleApi()
        val call = service.getCallContributors(sName, sRepo)

        call.enqueue(object : Callback<List<Contributor>> {
            override fun onResponse(call: Call<List<Contributor>>, response: Response<List<Contributor>>) {
                if (response.isSuccessful) {
                    val contributors = response.body()

                    for (c in contributors!!) {
                        Log.i("log", c.toString())
                    }
                } else {
                    Log.i("log", "not successful")
                }
            }

            override fun onFailure(call: Call<List<Contributor>>, t: Throwable) {
                Log.i("log", t.message.toString())
            }
        })
    }

    // retrofit + okHttp + rxJava
    private fun startRx() {
        val service = RestfulAdapter.getServiceApi()
        val observable = service.getObContributors(sName, sRepo)

        mCompositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Contributor>>() {
                    override fun onNext(contributors: List<Contributor>) {
                        for (c in contributors) {
                            Log.i("log", c.toString())
                        }
                    }

                    override fun onError(t: Throwable) {
                        Log.i("log", t.message.toString())
                    }

                    override fun onComplete() {
                        Log.i("log", "complete")
                    }
                })
        )
    }

    private fun test() {
        RestfulAdapter.getServiceApi().getObContributors(sName, sRepo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { s -> Log.i("i", s.toString()) }
    }

}
