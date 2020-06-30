package com.example.viewpagerexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list: ArrayList<View> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.add(layoutInflater.inflate(R.layout.view1, null))
        list.add(layoutInflater.inflate(R.layout.view2, null))
        list.add(layoutInflater.inflate(R.layout.view3, null))

        pager.adapter = ViewPagerAdapter()

        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                // 스크롤 효과가 일어나면 동작한다.

                textView.text = "${position + 1} 번째 화면 입니다."
            }

            override fun onPageScrollStateChanged(state: Int) {

                // state 값은 0, 1, 2로 3가지 이다.

                // 0 : SCROLL_STATE_IDLE - 종료 되었을 때
                // 1 : SCROLL_STATE_DRAGGING - 드래그 되고 있을 때
                // 2 : SCROLL_STATE_SETTLING - 고정 되었을 때

                // 0번째 페이지에서 2번째 페이지를 누르면 0과 2만 찍힌다.
            }

            override fun onPageSelected(position: Int) {
                // 선택된 페이지를 알려준다.
            }

        })
    }

    inner class ViewPagerAdapter : PagerAdapter() {

        override fun getCount(): Int = list.size

        override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            pager.addView(list[position])

            return list[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) = pager.removeView(obj as View)
    }
}