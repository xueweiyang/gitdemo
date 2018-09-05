package com.example.fcl.kotlindemo.live.edit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fcl.kotlindemo.R
import com.example.fcl.kotlindemo.live.util.obtainViewModel
import com.example.fcl.kotlindemo.live.util.replaceFragmentInActivity

class AddEditTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_task)

        replaceFragmentInActivity(obtainViewFragment(),R.id.contentFrame)
    }

    private fun obtainViewFragment() = supportFragmentManager.findFragmentById(R.id.contentFrame) ?:
        AddEditTaskFragment.newInstance().apply {
            arguments = Bundle().apply {
                putString(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID,
                    intent.getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID))
            }
        }

    fun obtainViewModel() :AddEditTaskViewModel=obtainViewModel(AddEditTaskViewModel::class.java)

    companion object {
        const val REQUEST_CODE = 1
    }
}
