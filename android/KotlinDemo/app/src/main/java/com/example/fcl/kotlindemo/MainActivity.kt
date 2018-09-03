package com.example.fcl.kotlindemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.testView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        log("5+1="+execute(5,Operation.Add(1)))
        log("5-1="+execute(5,Operation.Substract(1)))
        log("5 increment ="+execute(5,Operation.Increment))
        log("5 decrement ="+execute(5,Operation.Decrement))


    }

    fun move(view: View) {
        val ui = Ui(emptyList())+UiOp.Hide  +UiOp.TranslateX(20f)+UiOp.TranslateY(40f)+ UiOp.Show
        run(testView, ui)
    }

    fun run(view: View,ui: Ui) {
        ui.uiOps.forEach { executeUi(view, it) }
    }

    fun log(content:String) {
        Log.e("MainActivity", content)
    }

    fun execute(x:Int, op:Operation)  = when(op) {
        is Operation.Add -> x+op.value
        is Operation.Substract -> x-op.value
         Operation.Increment->x+1
         Operation.Decrement->x-1
    }
}

fun executeUi(view: View, op: UiOp) =when(op) {
    UiOp.Show -> view.visibility = View.VISIBLE
    UiOp.Hide -> view.visibility = View.GONE
   is UiOp.TranslateX -> view.translationX=op.px
   is UiOp.TranslateY -> view.translationY=op.px
}
