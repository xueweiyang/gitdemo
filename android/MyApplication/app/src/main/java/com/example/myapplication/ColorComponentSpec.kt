package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.facebook.litho.*
import com.facebook.litho.annotations.*

@MountSpec(poolSize = 3,canPreallocate = true,isPureRender = true)
object ColorComponentSpec {

//    @OnPrepare
//    fun onPrepare(
//        context: ComponentContext,
//        @Prop colorName: String,
//        color: Output<Int>
//    ) {
//        color.set(Color.parseColor(colorName))
//    }

    @OnCreateMountContent
    fun onCreateMountContent(c: Context): ColorDrawable {
        return ColorDrawable()
    }

    @OnMount
    fun onMount(
        context: ComponentContext,
        colorDrawable: ColorDrawable,
        @Prop colorName: String
    ) {
        colorDrawable.color = Color.parseColor(colorName)
    }

    @OnMeasure
    fun onMeasure(
        context: ComponentContext,
        layout: ComponentLayout,
        widthSpec: Int,
        heightSpec: Int,
        size: Size
    ) {
        if (SizeSpec.getMode(widthSpec) == SizeSpec.UNSPECIFIED) {
            size.width = 40
        } else {
            size.width = SizeSpec.getSize(widthSpec)
        }

        if (SizeSpec.getMode(heightSpec) == SizeSpec.UNSPECIFIED) {
            size.height = (size.width * 1.5).toInt()
        } else {
            size.height = SizeSpec.getSize(heightSpec)
        }
    }

    @ShouldUpdate(onMount = true)
    fun shouldUpdate(@Prop someStringProp: Diff<String>): Boolean {
        return !someStringProp.previous.equals(someStringProp.next)
    }

}