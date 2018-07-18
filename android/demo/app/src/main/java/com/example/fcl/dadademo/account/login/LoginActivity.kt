package com.example.fcl.dadademo.account.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fcl.dadademo.account.login.LoginContract.Presenter
import com.example.fcl.dadademo.api.RpcHttpException
import com.example.fcl.dadademo.util.ToastHelper
import com.example.fcl.dadademo.util.extension.isPhone
import com.example.fcl.kotlindemo.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_login.loginCloseView
import kotlinx.android.synthetic.main.activity_login.loginCountryCode
import kotlinx.android.synthetic.main.activity_login.loginPhoneView
import kotlinx.android.synthetic.main.activity_login.loginPwdView
import kotlinx.android.synthetic.main.activity_login.loginView
import kotlinx.android.synthetic.main.layout_contrain_activity.view.editText

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private var isChina: Boolean = false
        get() {
            return loginCountryCode.text.toString() == "+86"
        }

    override fun onRequestError(e: RpcHttpException?) {
        ToastHelper.toast("登录失败"+e?.message)
    }

    override fun onPasswordLoginSucceed() {
        ToastHelper.toast("登录成功")
        finish()
    }

    private lateinit var presenter: LoginContract.Presenter

    override fun bindPresenter(presenter: Presenter) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initPresenter()
        setupVerify()
    }

    private fun setupVerify() {
        val observableMobile = RxTextView.textChanges(loginPhoneView).map {
            it.toString().replace(" ", "").isPhone(isChina)
        }
        val observablePassword = RxTextView.textChanges(loginPwdView).map {
            it.toString().isNotEmpty()
        }
        passwordLoginVerify(observableMobile, observablePassword)
    }

    private fun passwordLoginVerify(
        observableMobile: Observable<Boolean>,
        observablePassword: Observable<Boolean>
    ) {
        Observables
            .combineLatest(observableMobile, observablePassword) { mobile, password ->
                mobile && password
            }
            .subscribe { isPasswordVerify ->
                RxView.enabled(loginView).accept(isPasswordVerify)
            }
    }

    private fun initPresenter() {
        val loginPresenter = LoginPresenter(this)
        loginPresenter.subscribe()
    }

    private fun initView() {
        loginCloseView.setOnClickListener {
            finish()
        }
        loginView.setOnClickListener {
            ToastHelper.toast("点击")
            presenter.passwordLogin("+86"+loginPhoneView.text.toString(), loginPwdView.text.toString())
        }
    }
}
