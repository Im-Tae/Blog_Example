package com.leaf.rxandroid.fragments

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : Fragment() {

    private var mItems: ArrayList<RecyclerItem> = arrayListOf()
    private lateinit var mRecyclerViewAdapter : RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerViewAdapter = RecyclerViewAdapter(mItems)
        recycler_view.adapter = mRecyclerViewAdapter
        mRecyclerViewAdapter.getItemPublishSubject().subscribe { s -> Toast.makeText(context, s.title, Toast.LENGTH_SHORT).show() }
    }

    override fun onStart() {
        super.onStart()

        getItemObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mRecyclerViewAdapter.updateItems(item)
                mRecyclerViewAdapter.notifyDataSetChanged()
            }
    }

    private fun getItemObservable(): Observable<RecyclerItem> {
        val pm = activity!!.packageManager
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        return Observable.fromIterable(pm.queryIntentActivities(i, 0))
            .sorted(ResolveInfo.DisplayNameComparator(pm))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { item ->
                val image = item.activityInfo.loadIcon(pm)
                val title  = item.activityInfo.loadLabel(pm).toString()

                return@map RecyclerItem(image, title)
            }
    }

}
