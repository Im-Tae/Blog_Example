package com.example.customadapterexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val data = arrayOf("1번째", "2번째", "3번째", "4번째", "5번째", "6번째", "7번째")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val adapter = ArrayAdapter(this, R.layout.custom_list, R.id.list_text, data)

        val adapter = ListAdapter()

        listView.adapter = adapter
    }

    inner class ListAdapter: BaseAdapter() {

        override fun getCount(): Int = data.size

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {

            var convertView: View? = view

            if (view == null) {
                convertView = layoutInflater.inflate(R.layout.custom_list, null)
            }

            val text: TextView? = convertView?.findViewById(R.id.list_text)
            val button1: Button? = convertView?.findViewById(R.id.button1)
            val button2: Button? = convertView?.findViewById(R.id.button2)

            button1?.setOnClickListener(ButtonClickListener())
            button2?.setOnClickListener(ButtonClickListener())

            button1?.tag = position
            button2?.tag = position

            text?.text = data[position]

            return convertView
        }

        override fun getItem(position: Int): Any? = data[position]

        override fun getItemId(position: Int): Long = position.toLong()
    }

    inner class ButtonClickListener: View.OnClickListener {

        override fun onClick(v: View?) {

            val position = v?.tag as Int + 1

            when(v.id) {
                R.id.button1 -> textView.text = "${position}번째에 있는 1번 버튼 클릭"
                R.id.button2 -> textView.text = "${position}번째에 있는 2번 버튼 클릭"
            }
        }
    }
}