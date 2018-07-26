package com.example.fcl.daggerdemo;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MyApp app;

    public AppModule(MyApp app) {
        this.app = app;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences("spfile", Context.MODE_PRIVATE);
    }

    @Provides
    MyApp provideApp() {
        return app;
    }

}
