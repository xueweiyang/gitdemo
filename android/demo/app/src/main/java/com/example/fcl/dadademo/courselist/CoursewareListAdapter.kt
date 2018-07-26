package com.example.fcl.dadademo.courselist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.fcl.dadademo.courselist.CoursewareListAdapter.ViewHolder
import com.example.fcl.dadademo.model.Courseware
import com.example.fcl.dadademo.util.Constant
import com.example.fcl.dadademo.util.ImageLoader
import com.example.fcl.dadademo.util.extension.realImageUrl
import com.example.fcl.kotlindemo.R

class CoursewareListAdapter(private val context: Context, private var courseList: List<Courseware>) :
    RecyclerView.Adapter<ViewHolder>() {
    var onUnLockItemClickCallback: OnUnLockItemClickListener? = null
    var onLockedItemClickCallback: OnLockedItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_courseware_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        val courseware = courseList[pos]

        holder.nameTv.text = courseware.courseName
        holder.numTv.text = String.format("%1s/%2s", courseware.soundFinish, courseware.soundTotal)
        ImageLoader.loadImage(courseware.courseImage.realImageUrl(), holder.coverImg)
        holder.shadowImg.visibility = if (courseware.courseLock == Constant.EVALUATE_COURSE_WARE_LOCK) View.VISIBLE
        else View.GONE
        holder.itemView?.setOnClickListener {
            if (courseware.courseLock==Constant.EVALUATE_COURSE_WARE_LOCK) {
                //加锁
                onLockedItemClickCallback?.invoke(courseware)
            } else{
                onUnLockItemClickCallback?.invoke(pos, courseware)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTv: TextView = view.findViewById(R.id.evaluateNameTv)
        val numTv: TextView = view.findViewById(R.id.evaluateNumTv)
        val coverImg: ImageView = view.findViewById(R.id.evaluateCoverImage)
        val shadowImg: ImageView = view.findViewById(R.id.evaluateShadowImage)
    }

    fun updateData(data: List<Courseware>, needRefresh: Boolean) {
        if (needRefresh) {
            courseList = data
            notifyDataSetChanged()
        } else {
            courseList = courseList.plus(data)
            notifyItemRangeInserted(itemCount - 1, data.size)
        }
    }
}

typealias OnUnLockItemClickListener = ((Int, courseware:Courseware)->Unit)
typealias OnLockedItemClickListener = ((courseware:Courseware)->Unit)