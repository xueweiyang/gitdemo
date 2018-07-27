package com.example.fcl.dadademo.evaluatelist

import android.arch.persistence.db.SupportSQLiteOpenHelper.Factory
import android.view.View
import com.dadaabc.zhuozan.dadaabcstudent.model.VoicePractise
import com.example.fcl.dadademo.provider.EvaluateScoreDbHelper

class EvaluatePresenter(private val view: View) : EvaluateContract.Presenter{
    override fun fetchEvaluateList(courseId: String, bookCategoryId: String) {
        EvaluateScoreDbHelper()
    }

    override fun addListenTime(voicePractise: VoicePractise) {

    }

    override fun updatePractiseVoiceRecord(voicePractise: VoicePractise) {

    }

    override fun upLoadEvaluateError(errorId: Int, voicePractiseId: Int) {

    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }
}