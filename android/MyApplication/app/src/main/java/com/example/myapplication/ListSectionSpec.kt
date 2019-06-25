package com.example.myapplication

import android.graphics.Color
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection

@GroupSectionSpec
object ListSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(c : SectionContext):Children{
        val builder = Children.create()
        for (i in 0..32){
            builder.child(
                SingleComponentSection.create(c)
                    .key(i.toString())
                    .component(ListItem.create(c)
                        .color(if (i%2==0) Color.WHITE else Color.LTGRAY)
                        .title("hello $i")
                        .subTitle("litho")
                        .build())
            )
        }
        return builder.build()
    }

}