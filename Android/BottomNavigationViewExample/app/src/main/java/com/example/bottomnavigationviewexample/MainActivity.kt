package com.example.bottomnavigationviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.registerOnPageChangeCallback(ViewPagerPageChangeCallback())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.page_home -> {
                viewPager.currentItem = 0
                return true
            }
            R.id.page_tv -> {
                viewPager.currentItem = 1
                return true
            }
            R.id.page_calendar -> {
                viewPager.currentItem = 2
                return true
            }
        }

        return false
    }

    private inner class ViewPagerPageChangeCallback : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {

            bottomNavigationView.selectedItemId = when(position) {
                0 -> R.id.page_home
                1 -> R.id.page_tv
                2 -> R.id.page_calendar
                else -> error("No id")
            }
        }
    }

    private inner class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> HomeFragment()
                1 -> TVFragment()
                2 -> CalendarFragment()
                else -> error("No Fragment")
            }
        }
    }
}