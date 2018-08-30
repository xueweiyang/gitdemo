package com.example.fcl.daggerdemo;

import dagger.Component;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = SimpleModule.class)
public interface SimpleComponent extends AndroidInjector<Main2Activity>{

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<Main2Activity> {

    }

}
