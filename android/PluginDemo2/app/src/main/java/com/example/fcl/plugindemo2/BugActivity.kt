package com.example.fcl.plugindemo2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class BugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bug)

    }

    fun click(view: View) {
        Toast.makeText(this, Cat().show(), Toast.LENGTH_SHORT).show()
    }
}
