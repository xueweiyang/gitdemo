package com.example.fcl.kotlindemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.datepicker.DatePickerDialogFragment
import com.example.datepicker.DatePickerDialogFragment.ClickListener
import com.example.fcl.dadademo.SplashActivity
import com.example.fcl.dadademo.drawable.PaletteActivity
import com.example.fcl.dadademo.drawable.RippleActivity
import com.example.fcl.kotlindemo.activity.DemoActivityKotlin
import kotlinx.android.synthetic.main.activity_main.changeui
import kotlinx.android.synthetic.main.activity_main.dialog
import kotlinx.android.synthetic.main.activity_main.toolbar

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

    private fun leak() {

startActivity(Intent(this, LeakActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("测试")?.setTitle("His")?.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    fun click(view: View) {
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show()
        when (view.id) {
            R.id.demo -> startActivity(Intent(this, DemoActivityKotlin::class.java))
            R.id.dialog -> dialog()
            R.id.leak -> leak()
            R.id.anr -> anr()
            R.id.changeui -> changeui()
            R.id.dadademo -> dadademo()
            R.id.platte -> startActivity(Intent(this, RippleActivity::class.java))
        }
//        dialog()
    }

    fun dadademo() {
        startActivity(Intent(this, SplashActivity::class.java))
    }

    fun changeui() {
        changeui.text = "修改"
    }

    fun anr() {
        Thread.sleep(20000)
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


}
