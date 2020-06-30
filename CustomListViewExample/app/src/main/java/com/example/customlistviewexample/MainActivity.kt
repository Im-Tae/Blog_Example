package com.example.customlistviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val image = arrayOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground)
    private val item = arrayOf("1", "2", "3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ArrayList<HashMap<String, Any>>()

        for (i in item.indices) {

            val map = HashMap<String, Any>()

            map.put("image", image[i])
            map.put("item", item[i])

            list.add(map)
        }

        val keys = arrayOf("image", "item")
        val ids = intArrayOf(R.id.image, R.id.text)

        val adapter = SimpleAdapter(this, list, R.layout.custom_listview, keys, ids)

        listView.adapter = adapter


        listView.onItemClickListener = ListListener()
    }

    inner class ListListener: AdapterView.OnItemClickListener {

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            textView.text = "위치 : ${item[position]}"
        }
    }
}