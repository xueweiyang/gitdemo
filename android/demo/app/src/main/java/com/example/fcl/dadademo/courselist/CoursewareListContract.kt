package com.example.fcl.dadademo.courselist

import com.example.fcl.dadademo.model.Courseware
import com.example.fcl.dadademo.mvp.BasePresenter
import com.example.fcl.dadademo.mvp.BaseView

interface CoursewareListContract{

    interface View:BaseView<Presenter> {

        fun initCoursewareList(coursewareList:List<Courseware>)
        fun showMoreCoursewareList(coursewareList: List<Courseware>)
    }

    interface Presenter : BasePresenter{

        fun fetchCoursewareList(page:Int,numRow:Int, bookIdEncrypt:String)

    }

}