package com.example.myapplication

import android.graphics.Color
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.SolidColor
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaAlign

@LayoutSpec
object MyComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(
        c: ComponentContext,
        @Prop color: Int,
        @Prop title: String
    ): Component =
        Row.create(c)
            .alignItems(YogaAlign.CENTER)
            .child(
                SolidColor.create(c)
                    .color(color)
                    .widthDip(40f)
                    .heightDip(40f)
            )
            .child(
                Text.create(c)
                    .text(title)
                    .textSizeSp(20f)
                    .flexGrow(1f)
            )
            .build()


}