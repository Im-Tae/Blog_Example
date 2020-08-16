package com.example.contextmenuexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val data = arrayOf("1", "2", "3", "4", "5", "6", "7")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, data)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, i, _ ->
            textView.text = "${i + 1} 번째 클릭"
        }

        registerForContextMenu(textView)
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        when(v?.id) {
            R.id.textView -> {
                menu?.setHeaderTitle("텍스트 뷰")
                menuInflater.inflate(R.menu.textview_menu, menu)
            }

            R.id.listView -> {
                menu?.setHeaderTitle("리스트 뷰")
                menuInflater.inflate(R.menu.listview_menu, menu)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.item1 -> textView.text = "1번 클릭"
            R.id.item2 -> textView.text = "2번 클릭"

            R.id.listView_item1 -> textView.text = "리스트 뷰 1번 클릭"
            R.id.listView_item2 -> textView.text = "리스트 뷰 2번 클릭"
        }

        return super.onContextItemSelected(item)
    }
}