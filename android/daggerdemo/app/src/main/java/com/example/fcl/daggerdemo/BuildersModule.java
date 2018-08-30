package com.example.fcl.daggerdemo;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by galio.fang on 18-8-30
 */
@Module(subcomponents = {SimpleComponent.class})
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(Main2Activity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindSecondActivityInjectorFactory(SimpleComponent.Builder
        builder);

}
