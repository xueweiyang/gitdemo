package com.example.fcl.kotlindemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.datepicker.DatePickerDialogFragment
import com.example.datepicker.DatePickerDialogFragment.ClickListener
import com.example.fcl.kotlindemo.activity.DemoActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_main.dialog
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.reactivestreams.Subscriber

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
//            supportActionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("测试")?.setTitle("His")?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    fun click(view: View) {
        when (view.id) {
            R.id.demo -> startActivity(Intent(this, DemoActivity::class.java))
            R.id.dialog -> dialog()
        }
        dialog()
    }

    private fun dialog() {
        val dialogFragment = DatePickerDialogFragment()
        dialogFragment.show(supportFragmentManager, "datePickerDialog")
        dialogFragment.onClickListener = object : ClickListener{
            override fun ensure(date: String) {
                dialog.text = date
            }
        }
    }

    private fun testrx() {
        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("1")
            it.onComplete()
        }).map(Function<String, Int> {
            return@Function it.toInt()
        }).subscribe(object : Observer<Int> {
            override fun onComplete() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSubscribe(d: Disposable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(value: Int?) {
                Log.e(TAG, "res:" + value)
            }

            override fun onError(e: Throwable?) {
                Log.e(TAG, e.toString())
            }
        })
    }
}
