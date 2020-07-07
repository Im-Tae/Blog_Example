package com.leaf.rxandroid.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leaf.rxandroid.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RecyclerViewAdapter(private var mItems: ArrayList<RecyclerItem>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var mPublishSubject : PublishSubject<RecyclerItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]

        holder.mImage?.setImageDrawable(item.image)
        holder.mTitle?.text = item.title
        holder.getClickObservable(item).subscribe(mPublishSubject)
    }

    override fun getItemCount(): Int = mItems.size

    fun updateItems(items: ArrayList<RecyclerItem>) = mItems.addAll(items)

    fun updateItems(item: RecyclerItem) = mItems.add(item)

    fun getItemPublishSubject() : PublishSubject<RecyclerItem> = mPublishSubject

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val mImage = itemView?.findViewById<ImageView>(R.id.item_image)
        val mTitle = itemView?.findViewById<TextView>(R.id.item_title)

        fun getClickObservable(item: RecyclerItem) : Observable<RecyclerItem> {
            return Observable.create { e -> itemView.setOnClickListener { e.onNext(item) } }
        }
    }
}