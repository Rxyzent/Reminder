package com.rxyzent.remainder.core.di.module.ui.activity

import com.rxyzent.remainder.core.di.module.ui.details.DetailsActivityModule
import com.rxyzent.remainder.core.di.module.ui.details.DetailsFragmentProviders
import com.rxyzent.remainder.core.di.module.ui.main.MainActivityModule
import com.rxyzent.remainder.core.di.module.ui.main.MainFragmentProviders
import com.rxyzent.remainder.ui.details.DetailsActivity
import com.rxyzent.remainder.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ContributesAndroidInjector(
        modules = [MainActivityModule::class, MainFragmentProviders::class]
    )
    fun provideMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [DetailsActivityModule::class, DetailsFragmentProviders::class]
    )
    fun provideDetailsActivity(): DetailsActivity

}