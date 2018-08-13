package com.example.fcl.dadademo.dagger

import com.example.fcl.kotlindemo.MyApp
import dagger.Module
import dagger.Provides

/**
 * Created by galio.fang on 18-8-7
 */

@Module
class AppModule(val myApp: MyApp) {

    @Provides
    fun providesApp() : MyApp {
        return myApp
    }

}