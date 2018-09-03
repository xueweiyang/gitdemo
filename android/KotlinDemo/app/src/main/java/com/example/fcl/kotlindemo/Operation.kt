package com.example.fcl.kotlindemo

/**
 * Created by galio.fang on 18-9-3
 */
sealed class Operation {

    class Add(val value:Int) :Operation()
    class Substract(val value:Int) :Operation()
    object Increment:Operation()
    object Decrement:Operation()
}
