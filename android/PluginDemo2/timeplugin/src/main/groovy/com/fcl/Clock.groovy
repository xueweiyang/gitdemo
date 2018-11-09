package com.fcl

class Clock {

    long startTime

    Clock() {
        this(System.currentTimeMillis())
    }

    Clock(long startTime) {
        this.startTime = startTime
    }

    long getTime() {
        return System.currentTimeMillis() - startTime
    }

}
