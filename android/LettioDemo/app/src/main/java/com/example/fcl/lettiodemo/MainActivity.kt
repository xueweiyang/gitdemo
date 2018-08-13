package com.example.fcl.lettiodemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieComposition
import kotlinx.android.synthetic.main.activity_main.starView
import kotlinx.android.synthetic.main.activity_main.trueView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    fun init() {
//        LottieComposition.Factory.fromAssetFileName(this, "star.json") {
//            starView.setComposition(it)
//        }
        LottieComposition.Factory.fromAssetFileName(this, "sahua.json") {
            it?.let {
                starView.imageAssetsFolder = "images/"
                starView.setComposition(it)
            }
        }
        LottieComposition.Factory.fromAssetFileName(this, "right.json") {
            it?.let {
                trueView.imageAssetsFolder = "images/"
                trueView.setComposition(it)
            }
        }
    }

    fun click(view:View) {
        init()
    }
}
