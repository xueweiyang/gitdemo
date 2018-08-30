package com.example.fcl.daggerdemo;

import android.content.SharedPreferences;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AppModule.class,
    AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<MyApp>{

    SharedPreferences sharedPreferences();

    MyApp myApp();

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MyApp> {

    }

}
