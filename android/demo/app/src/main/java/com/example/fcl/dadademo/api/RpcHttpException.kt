package com.example.fcl.dadademo.api

import java.io.IOException

class RpcHttpException(val errorCode:Long,val errorMessage:String?):IOException() {
    override fun toString(): String {
        return "{$errorMessage,$errorCode}"
    }
}

class UnauthorizedException(val errorMessage: String?) : IOException()