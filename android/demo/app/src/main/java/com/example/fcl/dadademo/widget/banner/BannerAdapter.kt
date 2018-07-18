package com.example.fcl.dadademo.widget.banner

import android.database.DataSetObserver
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

internal class BannerAdapter(private val wrappedAdapter: PagerAdapter) : PagerAdapter() {

    private var realCount: Int = 0
    private val observer: DataSetObserver by lazy {
        object : DataSetObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
            }
        }
    }

    init {
        wrappedAdapter.registerDataSetObserver(observer)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return when (position) {
            0 -> wrappedAdapter.instantiateItem(container, realCount - 1) as View
            count - 1 -> wrappedAdapter.instantiateItem(container, 0) as View
            else -> wrappedAdapter.instantiateItem(container, position - 1) as View
        }
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun getCount(): Int {
        realCount = wrappedAdapter.count
        return if (realCount > 1) realCount + 2 else realCount
    }
}