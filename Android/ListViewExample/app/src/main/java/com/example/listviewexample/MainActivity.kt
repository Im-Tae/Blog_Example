package com.example.listviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val item = arrayOf("1", "2", "3", "4", "5", "6", "7")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)

        listView.adapter = adapter
//
//        listView.setOnItemClickListener(object : AdapterView.OnItemClickListener{
//            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                textView.text = "위치 : ${item[position]}"
//            }
//        })
//
//        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ -> textView.text = "위치 : ${item[position]}" }

        listView.onItemClickListener = ListListener()

    }

    inner class ListListener: AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            textView.text = "위치 : ${item[position]}"
        }
    }
}