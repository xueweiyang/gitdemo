package com.example.fcl.dadademo.util

import android.app.Activity
import android.content.Intent
import com.example.fcl.dadademo.account.RegisterActivity
import com.example.fcl.dadademo.account.RxRegisterFragment
import com.example.fcl.dadademo.base.BaseActivity
import io.reactivex.subjects.ReplaySubject

class RegisterHelper {

    companion object {
        private val TAG = "RxRegisterFragment"
    }
    private var activity:Activity?=null

    fun register(
        activity: Activity,
        isRegisterType: Boolean = true,
        registerSubTitle: String = "",
        entranceName: String? = null
    ): ReplaySubject<Boolean> {
        this.activity=activity
        val subject = ReplaySubject.create<Boolean>()
        if (AccountManager.isLogin()) {
            subject.onNext(true)
            subject.onComplete()
        } else {
            val rxRegisterFragment = getRxRegisterFragment(activity)
            rxRegisterFragment.setSubject(subject)
            startRegisterActivity(rxRegisterFragment, isRegisterType, registerSubTitle, entranceName)
        }
        return subject
    }

    private fun startRegisterActivity(
        rxRegisterFragment: RxRegisterFragment,
        isRegisterType: Boolean,
        registerSubTitle: String,
        entranceName: String? = null
    ) {
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.putExtra(Constant.REGISTER_TYPE_EXTRA, isRegisterType)
        intent.putExtra(Constant.REGISTER_SUB_TITLE_EXTRA, registerSubTitle)
        intent.putExtra(BaseActivity.ENTRANCE_NAME_KEY, entranceName)
        rxRegisterFragment.startActivity(intent)
    }

    private fun getRxRegisterFragment(activity: Activity): RxRegisterFragment {
        var rxRegisterFragment = findRxRegisterFragment(activity)
        if (rxRegisterFragment == null) {
            rxRegisterFragment = RxRegisterFragment()
            val fragmentManager = activity.fragmentManager
            fragmentManager.beginTransaction()
                .add(rxRegisterFragment, TAG)
                .commitAllowingStateLoss()
        }
        return rxRegisterFragment
    }

    private fun findRxRegisterFragment(activity: Activity): RxRegisterFragment? {
        return activity.fragmentManager.findFragmentByTag(TAG) as? RxRegisterFragment
    }
}