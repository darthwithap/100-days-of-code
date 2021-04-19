package com.darthwithap.fragmentssection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPager : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.apply {
            add(FruitsFragment())
            add(VegetablesFragment())
            add(FruitsFragment())
            add(VegetablesFragment())
            add(FruitsFragment())
            add(VegetablesFragment())
        }

        viewPager.adapter = viewPagerAdapter
    }
}