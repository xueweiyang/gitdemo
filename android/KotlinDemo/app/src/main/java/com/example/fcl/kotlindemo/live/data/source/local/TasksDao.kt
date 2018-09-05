package com.example.fcl.kotlindemo.live.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.fcl.kotlindemo.live.data.Task

/**
 * Created by galio.fang on 18-9-5
 */
@Dao interface TasksDao {

    @Query("select * from Tasks") fun getTasks() :List<Task>

    @Query("select * from Tasks where entryid = :taskId") fun getTaskById(taskId:String):Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertTask(task: Task)
}
