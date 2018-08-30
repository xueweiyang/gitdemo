package com.example.fcl.daggerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerAppCompatActivity;
import javax.inject.Inject;

public class Main2Activity extends DaggerAppCompatActivity {
    String TAG=Main2Activity.class.getSimpleName();
    @Inject
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click(View view) {
        Log.e(TAG, student.getName());
        Toast.makeText(this, student.getName(), Toast.LENGTH_SHORT).show();
    }
}
