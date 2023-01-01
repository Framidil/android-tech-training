package com.framidil.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.main_view_pager)
        val itemList = listOf("First", "Second", "Third")
        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getCount() = Int.MAX_VALUE

            override fun getItem(position: Int) = ViewPagerFragment.newInstance(itemList[position % itemList.size])

        }
    }
}