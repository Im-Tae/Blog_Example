package com.example.actionbarexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.item1 -> textView1.text = "1번 클릭"
            R.id.item2 -> textView1.text = "2번 클릭"
            R.id.item3 -> textView1.text = "3번 클릭"
            R.id.item4 -> textView1.text = "4번 클릭"
            R.id.item5 -> textView1.text = "5번 클릭"
            R.id.item6 -> textView1.text = "6번 클릭"
            R.id.item7 -> textView1.text = "7번 클릭"
        }

        return super.onOptionsItemSelected(item)
    }
}