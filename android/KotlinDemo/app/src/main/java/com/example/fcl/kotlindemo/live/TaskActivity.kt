package com.example.fcl.kotlindemo.live

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.live.edit.AddEditTaskActivity
import com.example.fcl.kotlindemo.live.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_task.addTask

class TaskActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        initView()

        viewModel=obtainViewModel().apply {
            newTaskEvent.observe(this@TaskActivity, Observer<Void> {
                this@TaskActivity.addNewTask()
            })
        }

    }

    fun addNewTask() {
        val intent = Intent(this, AddEditTaskActivity::class.java)
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_CODE)
    }

    private fun initView() {
        addTask.setOnClickListener {

        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.contentFrame, TaskFragment())
            .commit()
    }

    fun obtainViewModel() :TaskViewModel=obtainViewModel(TaskViewModel::class.java)
}
