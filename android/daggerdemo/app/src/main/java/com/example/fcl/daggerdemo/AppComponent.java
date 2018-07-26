package com.example.fcl.daggerdemo;

import android.content.SharedPreferences;
import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {

    SharedPreferences sharedPreferences();

    MyApp myApp();

}
