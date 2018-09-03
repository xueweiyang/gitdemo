package com.example.fcl.kotlindemo

/**
 * Created by galio.fang on 18-9-3
 */
class Ui(val uiOps :List<UiOp>) {

    operator fun plus(uiOp:UiOp) = Ui(uiOps+uiOp)

}
