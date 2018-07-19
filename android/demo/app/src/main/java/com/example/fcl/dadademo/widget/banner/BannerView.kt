package com.example.fcl.dadademo.widget.banner

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class BannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun setAdapter(adapter: PagerAdapter?) {
        if (null==adapter){
            return
        }
        val bannerAdapter=BannerAdapter(adapter)
        super.setAdapter(bannerAdapter)

    }

}