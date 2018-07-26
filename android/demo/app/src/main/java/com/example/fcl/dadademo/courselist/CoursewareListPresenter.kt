package com.example.fcl.dadademo.courselist

import com.example.fcl.dadademo.api.ApiService
import com.example.fcl.dadademo.courselist.CoursewareListContract.View
import com.example.fcl.dadademo.util.RxSchedulerUtils
import com.example.fcl.dadademo.util.extension.subscribeRemoteData

class CoursewareListPresenter(private val view:View) : CoursewareListContract.Presenter{
    override fun fetchCoursewareList(page: Int, numRow: Int, bookIdEncrypt: String) {
        ApiService.fetchCoursewareList(page, numRow, bookIdEncrypt)
            .compose(RxSchedulerUtils.ioToMainSchedulers())
            .subscribeRemoteData({
                it?.let {
                    if (page>0){
                        view.showMoreCoursewareList(it.data)
                    } else{
                        view.initCoursewareList(it.data)
                    }
                }
            }, {
                it?.printStackTrace()
            })
    }

    override fun subscribe() {
    }

    override fun unSubscribe() {
    }
}