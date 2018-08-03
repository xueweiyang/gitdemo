package com.example.fcl.roomdemo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class User{

    @PrimaryKey
    var uid :Int=0

    @ColumnInfo(name = "first_name")
    var firstName :String=""

    @ColumnInfo(name = "last_name")
    var lastName :String=""

}