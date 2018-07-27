package com.example.fcl.dadademo.courselist

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.dadademo.courselist.CoursewareListContract.Presenter
import com.example.fcl.dadademo.evaluatelist.EvaluateListActivity
import com.example.fcl.dadademo.model.Courseware
import com.example.fcl.dadademo.util.Constant
import com.example.fcl.dadademo.util.ToastHelper
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.layout_course_list_activity.coursewareListRecyclerView

class CoursewareListActivity : BaseActivity(),CoursewareListContract.View {
    override fun showMoreCoursewareList(coursewareList: List<Courseware>) {

    }

    private var presenter:CoursewareListContract.Presenter?=null
    private var adapter:CoursewareListAdapter?=null

    override fun initCoursewareList(coursewareList: List<Courseware>) {
        if (null==adapter){
            adapter= CoursewareListAdapter(this,coursewareList)
            adapter?.onUnLockItemClickCallback = {index,it->
                val coursewareIntent = Intent(this, EvaluateListActivity::class.java)
                coursewareIntent.putExtra(Constant.EVALUATE_COURSE_ID_EXTRA, it.courseId)
                startActivity(coursewareIntent)
            }
            coursewareListRecyclerView.adapter=adapter
        } else{
            adapter?.updateData(coursewareList, true)
        }
    }

    override fun bindPresenter(presenter: Presenter) {
    this.presenter=presenter
        presenter.fetchCoursewareList(0,10,
            intent.getStringExtra(Constant.EVALUATE_BOOK_CATEGORY_ID_EXTRA))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_course_list_activity)
        title = intent.getStringExtra(Constant.EVALUATE_BOOK_CATEGORY_NAME_EXTRA)
        bindPresenter(CoursewareListPresenter(this))
        coursewareListRecyclerView.layoutManager=GridLayoutManager(this,2)

    }

}