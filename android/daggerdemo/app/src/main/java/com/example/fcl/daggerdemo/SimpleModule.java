package com.example.fcl.daggerdemo;

import dagger.Module;
import dagger.Provides;

@Module
public class SimpleModule {

    private Main2Activity activity;

    public SimpleModule(Main2Activity activity) {
        this.activity = activity;
    }

    @Provides
    Student provideStudent() {
        return new Student();
    }
}
