package com.example.fcl.dadademo.evaluatelist

import com.dadaabc.zhuozan.dadaabcstudent.model.VoicePractise
import com.example.fcl.dadademo.api.RpcHttpException
import com.example.fcl.dadademo.model.VoicePracticeErrorModel
import com.example.fcl.dadademo.mvp.BasePresenter
import com.example.fcl.dadademo.mvp.BaseView

interface EvaluateContract{

    interface View : BaseView<Presenter> {

        fun showEvaluateList(voicePractiseList: ArrayList<VoicePractise>)

        fun updateEvaluateView(voicePractise: VoicePractise)

        fun readVoicePracticeErrorModel(model: VoicePracticeErrorModel)

        fun onUpLoadEvaluateErrorSuccess()

        fun onUpLoadEvaluateErrorFail(e: RpcHttpException?)
    }

    interface Presenter : BasePresenter {

        fun fetchEvaluateList(courseId: String, bookCategoryId: String)

        fun addListenTime(voicePractise: VoicePractise)

        fun updatePractiseVoiceRecord(voicePractise: VoicePractise)

        fun upLoadEvaluateError(errorId: Int, voicePractiseId: Int)
    }

}