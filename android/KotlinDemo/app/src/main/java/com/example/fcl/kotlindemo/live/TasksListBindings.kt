package com.example.fcl.kotlindemo.live

import android.databinding.BindingAdapter
import android.util.Log
import android.widget.ListView
import com.example.fcl.kotlindemo.live.data.Task

/**
 * Created by galio.fang on 18-9-5
 */
object TasksListBindings{

    @BindingAdapter("app:items")
@JvmStatic fun setItems(listView: ListView,items:List<Task>) {
        Log.e("TasksListBindings","item size:"+items.size)
        with(listView.adapter as TasksAdapter) {
            replaceData(items)
        }
    }

}
