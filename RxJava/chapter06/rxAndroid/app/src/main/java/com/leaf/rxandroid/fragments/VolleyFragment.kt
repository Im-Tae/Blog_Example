package com.leaf.rxandroid.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.leaf.rxandroid.R
import com.leaf.rxandroid.volley.LocalVolley
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_volley.*
import org.json.JSONObject
import java.util.concurrent.ExecutionException

class VolleyFragment : Fragment() {

    private val URL = "http://time.jsontest.com/"
    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_volley, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vf_btn_get.setOnClickListener { post(getObservable()) }

        vf_btn_get2.setOnClickListener { post(getObservableFromCallable()) }

        vf_btn_get3.setOnClickListener { post(getObservableFromFuture()) }
    }

    private fun getFuture(): RequestFuture<JSONObject> {
        val future = RequestFuture.newFuture<JSONObject>()
        val req = JsonObjectRequest(URL, null, future, future)
        LocalVolley.getRequestQueue().add(req)
        return future
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private fun getData(): JSONObject = getFuture().get()

    private fun post(observable: Observable<JSONObject>) {
        val observer =getObserver()

        mCompositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer)
        )
    }

    private fun getObserver() : DisposableObserver<JSONObject> {
        return object : DisposableObserver<JSONObject>() {

            override fun onNext(jsonObject : JSONObject) {
                Log.i("log", jsonObject.toString())
            }

            override fun onError(e: Throwable) {
                Log.e("log", e.toString())
            }

            override fun onComplete() {
                Log.i("log", "complete")
            }
        }
    }

    private fun getObservable(): Observable<JSONObject> {
        return Observable.defer {
            try {
                return@defer Observable.just(getData())
            } catch (e: InterruptedException) {
                Log.e("log", e.message.toString())
                return@defer Observable.error<JSONObject>(e)
            } catch (e: ExecutionException) {
                Log.e("log", e.cause.toString())
                return@defer Observable.error<JSONObject>(e)
            }
        }
    }

    private fun getObservableFromCallable(): Observable<JSONObject> = Observable.fromCallable(this::getData)

    private fun getObservableFromFuture(): Observable<JSONObject> = Observable.fromFuture(getFuture())
}
