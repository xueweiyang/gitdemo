package com.example.myapplication

import android.app.LauncherActivity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.facebook.litho.widget.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = ComponentContext(this)
        val builder = Text.create(context)
        val component = builder.text("hello world")
            .textSizeDip(40f)
            .textColor(Color.parseColor("#666666"))
            .textAlignment(Layout.Alignment.ALIGN_CENTER)
            .build()
        val view = LithoView.create(context, component)
//        setContentView(view)

        val componentListItem = ListItem.create(context).title("hello")
            .subTitle("litho").build()
        val listItemView = LithoView.create(context, componentListItem)

        val componentList = RecyclerCollectionComponent
            .create(context)
            .disablePTR(true)
            .section(ListSection.create(SectionContext(context)).build())
            .build()
        val listView = LithoView.create(context, componentList)

        val myComponent = MyComponent
            .create(context)
            .title("smsmsmsm")
            .color(Color.RED)
            .build()
        val myView = LithoView.create(context, myComponent)

        
        setContentView(listView)

    }
}
