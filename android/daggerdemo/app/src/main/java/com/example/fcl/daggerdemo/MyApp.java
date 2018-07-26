package com.example.fcl.daggerdemo;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        AppComponent appComponent = DaggerAppComponent
            .builder()
            .appModule(new AppModule(this))
            .build();

    }
}
