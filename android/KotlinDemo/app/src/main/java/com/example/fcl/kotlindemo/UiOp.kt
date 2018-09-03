package com.example.fcl.kotlindemo

/**
 * Created by galio.fang on 18-9-3
 */
sealed class UiOp {

    object Show:UiOp()
    object Hide:UiOp()
    class TranslateX(val px:Float):UiOp()
    class TranslateY(val px:Float):UiOp()

}
