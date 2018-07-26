package com.example.fcl.dadademo.home

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.AppCompatImageView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType.CENTER_CROP
import com.example.fcl.dadademo.model.Ad
import com.example.fcl.dadademo.util.ImageLoader
import com.example.fcl.dadademo.util.extension.DefaultParams
import com.example.fcl.dadademo.util.extension.realImageUrl

class HomeBannerAdapter(val context: Context?,private val bannerAds:List<Ad>) :PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view==obj
    }

    override fun getCount(): Int {
        return bannerAds.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val ad = bannerAds[position]
        val imageView = AppCompatImageView(container.context)
        imageView.scaleType=CENTER_CROP
        container.addView(imageView, DefaultParams.MATCH_PARENT,DefaultParams.MATCH_PARENT)
        ImageLoader.loadImage(ad.imageUrl.realImageUrl(), imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}