package com.example.fcl.dadademo.evaluatelist

import android.os.Bundle
import com.dadaabc.zhuozan.dadaabcstudent.model.VoicePractise
import com.example.fcl.dadademo.api.RpcHttpException
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.dadademo.evaluatelist.EvaluateContract.Presenter
import com.example.fcl.dadademo.model.VoicePracticeErrorModel
import com.example.fcl.kotlindemo.R

class EvaluateListActivity : BaseActivity(),EvaluateContract.View{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_list)
    }

    override fun showEvaluateList(voicePractiseList: ArrayList<VoicePractise>) {


    }

    override fun updateEvaluateView(voicePractise: VoicePractise) {

    }

    override fun readVoicePracticeErrorModel(model: VoicePracticeErrorModel) {

    }

    override fun onUpLoadEvaluateErrorSuccess() {

    }

    override fun onUpLoadEvaluateErrorFail(e: RpcHttpException?) {

    }

    override fun bindPresenter(presenter: Presenter) {

    }
}