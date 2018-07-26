package com.example.fcl.daggerdemo;

import dagger.Component;

@Component(modules = SimpleModule.class)
public interface SimpleComponent {

    void inject(Main2Activity activity);

}
