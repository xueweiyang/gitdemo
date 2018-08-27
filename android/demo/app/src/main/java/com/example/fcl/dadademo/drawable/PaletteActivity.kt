package com.example.fcl.dadademo.drawable

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette
import com.example.fcl.dadademo.util.ImageLoader
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_palette.colorView
import kotlinx.android.synthetic.main.activity_palette.darkView
import kotlinx.android.synthetic.main.activity_palette.image
import kotlinx.android.synthetic.main.activity_palette.image2
import kotlinx.android.synthetic.main.activity_palette.layoutView
import kotlinx.android.synthetic.main.activity_palette.lioghtView

class PaletteActivity : AppCompatActivity() {
    val url = "http://p7xfia2m6.bkt.clouddn.com/bg_reservation_minor.png"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)
        ImageLoader.loadImage(R.mipmap.bg_reservation_minor, image)
        ImageLoader.loadImage(R.mipmap.bg_reservation_minor, image2)

        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.bg_reservation_minor)
        val builder = Palette.from(bitmap)
        builder.generate {
            val vibrant = it?.vibrantSwatch
//            colorView.setBackgroundColor(vibrant?.rgb ?: Color.WHITE)
//            lioghtView.setBackgroundColor(it?.lightVibrantSwatch?.rgb ?: Color.WHITE)
//            darkView.setBackgroundColor(it?.darkVibrantSwatch?.rgb ?: Color.WHITE)
        }

        layoutColor()
    }

    private fun layoutColor() {
        val bitmap = Bitmap.createBitmap(layoutView.width, layoutView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.translate(-layoutView.scrollX.toFloat(), -layoutView.scrollY.toFloat())
        layoutView.draw(canvas)
        val builder = Palette.from(bitmap)
        builder.generate {
            val vibrant = it?.vibrantSwatch
            colorView.setBackgroundColor(vibrant?.rgb ?: Color.WHITE)
            lioghtView.setBackgroundColor(it?.lightVibrantSwatch?.rgb ?: Color.WHITE)
            darkView.setBackgroundColor(it?.darkVibrantSwatch?.rgb ?: Color.WHITE)
        }
    }
}
