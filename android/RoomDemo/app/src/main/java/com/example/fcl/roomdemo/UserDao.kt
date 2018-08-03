package com.example.fcl.roomdemo

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): List<User>

    @Query("select * from user where uid in (:userIds)")
    fun loadAllByIds(userIds: Array<Int>): List<User>

    @Query("select * from user where first_name like :first and last_name like :last limit 1")
    fun findByName(first: String, last: String)

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user :User)

}