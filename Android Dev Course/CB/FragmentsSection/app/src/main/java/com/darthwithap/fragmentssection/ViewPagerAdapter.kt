package com.darthwithap.fragmentssection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    val fragments = arrayListOf<Fragment>()

    fun add(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getCount() = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}