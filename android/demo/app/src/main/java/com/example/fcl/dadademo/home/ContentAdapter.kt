package com.example.fcl.dadademo.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ContentAdapter @JvmOverloads constructor(
    val manager: FragmentManager,
    val fragments: List<Fragment>,
    val titles: Array<String>
) : FragmentStatePagerAdapter(manager) {
    override fun getItem(pos: Int): Fragment {
        return fragments[pos]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}