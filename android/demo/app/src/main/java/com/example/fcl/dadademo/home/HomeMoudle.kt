package com.example.fcl.dadademo.home

import dagger.Module
import dagger.Provides

/**
 * Created by galio.fang on 18-8-7
 */

@Module
class HomeMoudle(val homeView : HomeContract.View) {

    @Provides
    fun providePresenter() : HomePresenter {
        return HomePresenter(homeView)
    }

}