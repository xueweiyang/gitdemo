package com.example.fcl.daggerdemo;

import android.app.Application;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MyApp extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        //AppComponent appComponent = DaggerAppComponent
        //    .builder()
        //    .appModule(new AppModule(this))
        //    .build();

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
