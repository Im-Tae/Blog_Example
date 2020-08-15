package com.example.popupmemuexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val popup = PopupMenu(this, textView)

            menuInflater.inflate(R.menu.popup_menu, popup.menu)

            popup.setOnMenuItemClickListener(PopupListener())

            popup.show()
        }
    }

    inner class PopupListener: PopupMenu.OnMenuItemClickListener {

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId) {
                R.id.item1 -> textView.text ="1번 클릭"
                R.id.item2 -> textView.text ="2번 클릭"
                R.id.item3 -> textView.text ="3번 클릭"
            }

            return false
        }
    }
}