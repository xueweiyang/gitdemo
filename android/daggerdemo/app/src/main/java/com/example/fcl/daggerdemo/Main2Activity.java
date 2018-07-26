package com.example.fcl.daggerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import javax.inject.Inject;

public class Main2Activity extends AppCompatActivity {
    String TAG=Main2Activity.class.getSimpleName();
    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        DaggerSimpleComponent.builder()
            .simpleModule(new SimpleModule(this))
            .build()
            .inject(this);
    }

    public void click(View view) {
        Log.e(TAG, student.toString());
    }
}
