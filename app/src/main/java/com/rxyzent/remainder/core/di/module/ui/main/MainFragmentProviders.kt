package com.rxyzent.remainder.core.di.module.ui.main

import com.rxyzent.remainder.core.di.module.ui.fragments.home.HomeFragmentModule
import com.rxyzent.remainder.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainFragmentProviders {

    @ContributesAndroidInjector(modules = [
        HomeFragmentModule::class
    ])
    fun provideHomeFragment(): HomeFragment
}