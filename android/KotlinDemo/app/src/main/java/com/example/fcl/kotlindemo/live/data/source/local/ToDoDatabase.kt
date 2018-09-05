package com.example.fcl.kotlindemo.live.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.fcl.kotlindemo.live.data.Task

/**
 * Created by galio.fang on 18-9-5
 */
@Database(entities = arrayOf(Task::class), version = 1)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun taskDao() :TasksDao

    companion object {

        private var INSTANCE : ToDoDatabase?=null

        private val lock = Any()

        fun getInstance(context: Context) :ToDoDatabase{

            synchronized(lock) {
                if (INSTANCE==null) {
                    INSTANCE=Room.databaseBuilder(context.applicationContext,
                        ToDoDatabase::class.java,"Tasks.db")
                        .build()
                }
                return INSTANCE!!
            }

        }

    }

}
