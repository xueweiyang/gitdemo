package com.example.fcl.dadademo.evaluatelist

import android.os.Bundle
import android.view.Gravity
import com.dadaabc.zhuozan.dadaabcstudent.model.VoicePractise
import com.example.fcl.dadademo.api.RpcHttpException
import com.example.fcl.dadademo.base.BaseActivity
import com.example.fcl.dadademo.evaluatelist.EvaluateContract.Presenter
import com.example.fcl.dadademo.model.VoicePracticeErrorModel
import com.example.fcl.kotlindemo.R
import kotlinx.android.synthetic.main.activity_evaluate_list.drawerLayout
import kotlinx.android.synthetic.main.activity_evaluate_list.evaluateDrawerContentLayout
import kotlinx.android.synthetic.main.activity_evaluate_list.evaluateOpenDrawerView

class EvaluateListActivity : BaseActivity(),EvaluateContract.View{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_list)
        initView()
    }

    private fun initView() {
        evaluateOpenDrawerView.setOnClickListener {
            if (drawerLayout.isDrawerOpen(evaluateDrawerContentLayout)) {
                drawerLayout.closeDrawer(Gravity.END)
            } else{
                drawerLayout.openDrawer(Gravity.END)
            }
        }
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