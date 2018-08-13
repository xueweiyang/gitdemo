package com.example.fcl.dadademo.home

import dagger.Component

/**
 * Created by galio.fang on 18-8-7
 */

@Component(modules = arrayOf(HomeMoudle::class))
interface HomeComponent {

    fun inject(homeFragment: HomeFragment)

}